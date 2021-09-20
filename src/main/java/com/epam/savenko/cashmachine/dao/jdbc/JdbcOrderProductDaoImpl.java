package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.OrderProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.OrderProduct;
import com.epam.savenko.cashmachine.model.view.OrderView;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.OrderProduct.*;

public class JdbcOrderProductDaoImpl implements OrderProductDao {

    private static final Logger LOG = Logger.getLogger(JdbcOrderDaoImpl.class.getName());

    private static final String TABLE_NAME = "order_product";

    private static final String SQL_INSERT = "INSERT INTO order_product (order_id, product_id, quantity, price) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE order_product SET order_id=?, product_id=?, quantity=?, price=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM order_product WHERE id=?";
    private static final String SQL_SELECT_ALL_ORDER_PRODUCTS = "SELECT * FROM order_product";
    private static final String SQL_SELECT_ORDER_PRODUCT_BY_ID = "SELECT * FROM order_product WHERE id=?";
/*
    private static final String SQL_SELECT_SUM_BY_ORDER_ID = "SELECT sum(price*quantity)::money::numeric::float8 FROM order_product\n" +
            "WHERE order_id=?";
*/
    private static final String SQL_SELECT_SUM_BY_ORDER_ID = "SELECT sum(CASE\n" +
        "               WHEN p.weight = true\n" +
        "                   THEN (order_product.price::money::numeric::float8 * order_product.quantity) / 1000\n" +
        "               ELSE order_product.price::money::numeric::float8 * order_product.quantity\n" +
        "    END)\n" +
        "FROM order_product\n" +
        "         JOIN product p on p.id = order_product.product_id\n" +
        "WHERE order_product.order_id = ?";
    private static final String SQL_SELECT_ORDER_PRODUCT_VIEW_BY_ORDER_ID =
            "SELECT p.id AS id, p.name AS name, b.name AS brand_name, p.weight as weight, SUM(op.quantity) AS quantity, op.price::money::numeric::float8 AS price" +
                    " FROM order_product op" +
                    " JOIN product p on p.id = op.product_id" +
                    " JOIN brand b on b.id = p.brand_id" +
                    " WHERE order_id=?" +
                    " GROUP BY p.id, p.name, b.name, p.weight, op.price::money::numeric::float8" +
                    " ORDER BY p.name";

    public static final EntityMapper<OrderView.ProductInOrderView> mapProductInOrderViewRow = rs ->
            new OrderView.ProductInOrderView(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("brand_name"),
                    rs.getBoolean("weight"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"));
    public static final EntitiesMapper<OrderView.ProductInOrderView> mapProductInOrderViewRows = rs -> {
        List<OrderView.ProductInOrderView> list = new ArrayList<>();
        while (rs.next()) {
            list.add(mapProductInOrderViewRow.mapRow(rs));
        }
        return list;
    };

    private static final EntityMapper<OrderProduct> mapOrderProductRow = resultSet ->
            OrderProduct.newBuilder()
                    .setId(resultSet.getInt(ID))
                    .setOrderId(resultSet.getInt(ORDER_ID))
                    .setProductId(resultSet.getInt(PRODUCT_ID))
                    .setQuantity(resultSet.getInt(QUANTITY))
                    .setPrice(resultSet.getDouble(PRICE))
                    .build();

    public static final EntitiesMapper<OrderProduct> mapOrderProductRows = resultSet -> {
        List<OrderProduct> orderProducts = new ArrayList<>();
        while (resultSet.next()) {
            orderProducts.add(mapOrderProductRow.mapRow(resultSet));
        }
        return orderProducts;
    };

    JdbcEntity<OrderProduct> jdbcEntity;

    public JdbcOrderProductDaoImpl() {
        jdbcEntity = new JdbcEntity<>(mapOrderProductRows, TABLE_NAME, LOG);
    }

    @Override
    public List<OrderView.ProductInOrderView> getProductInOrderViewById(int id) throws CashMachineException {
        List<OrderView.ProductInOrderView> list = new ArrayList<>();
        try (Connection connection = ConnectionProvider.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ORDER_PRODUCT_VIEW_BY_ORDER_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                list = mapProductInOrderViewRows.mapList(rs);
            }
        } catch (SQLException e) {
            LOG.error("Error when receive data from ProductInOrderView", e);
            throw new CashMachineException("Error when receive data from ProductInOrderView", e);
        }
        return list;
    }

    @Override
    public double getSumByOrderId(int orderId) throws CashMachineException {
        double sum = 0;
        try (Connection conn = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_SELECT_SUM_BY_ORDER_ID)) {
            statement.setInt(1, orderId);
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()){
                  sum = rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            LOG.error("Error when calc sum for order id: " + orderId);
            throw new CashMachineException("Error when calc sum for order id: " + orderId, e);
        }
        return sum;
    }

    @Override
    public Optional<OrderProduct> findById(Integer integer) throws CashMachineException {
        return jdbcEntity.findById(integer, SQL_SELECT_ORDER_PRODUCT_BY_ID);
    }


    @Override
    public List<OrderProduct> findAll() throws CashMachineException {
        return jdbcEntity.findAll(SQL_SELECT_ALL_ORDER_PRODUCTS);
    }


    @Override
    public Optional<OrderProduct> insert(OrderProduct entity) throws CashMachineException {
        int id = insertOrderProduct(entity);
        entity.setId(id);
        return Optional.ofNullable(id > 0 ? entity : null);
    }

    private int insertOrderProduct(OrderProduct orderProduct) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, orderProduct.getOrderId());
            statement.setInt(2, orderProduct.getProductId());
            statement.setInt(3, orderProduct.getQuantity());
            statement.setBigDecimal(4, BigDecimal.valueOf(orderProduct.getPrice()));
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getInsert(TABLE_NAME))
                    .append(orderProduct);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return -1;
    }

    @Override
    public boolean update(OrderProduct entity) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_UPDATE)) {
            statement.setInt(1, entity.getOrderId());
            statement.setInt(2, entity.getProductId());
            statement.setInt(3, entity.getQuantity());
            statement.setDouble(4, entity.getPrice());
            statement.setInt(5, entity.getId());
            statement.executeUpdate();
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getUpdate(TABLE_NAME))
                    .append(entity.getId());
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return true;
    }

    @Override
    public boolean delete(int id) throws CashMachineException {
        return jdbcEntity.delete(SQL_DELETE, id);
    }


}