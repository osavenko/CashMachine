package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.OrderProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Order;
import com.epam.savenko.cashmachine.model.OrderProduct;
import com.epam.savenko.cashmachine.model.Product;
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


    private static final String SQL_SELECT_SUM_BY_ORDER_ID = "SELECT sum(CASE\n" +
            "               WHEN p.weight = true\n" +
            "                   THEN (order_product.price::money::numeric::float8 * order_product.quantity) / 1000\n" +
            "               ELSE order_product.price::money::numeric::float8 * order_product.quantity\n" +
            "    END)\n" +
            "FROM order_product\n" +
            "         JOIN product p on p.id = order_product.product_id\n" +
            "WHERE order_product.order_id = ?";
    private static final String SQL_SELECT_QUANTITY_FROM_ORDER_PRODUCT_BY_ORDER_ID_AND_PRODUCT_ID = "SELECT sum(quantity) FROM order_product WHERE order_id = ? AND product_id=?";

    private static final String SQL_SELECT_ORDER_PRODUCT_VIEW_BY_ORDER_ID =
            "SELECT p.id AS id, p.name AS name, b.name AS brand_name, p.weight as weight, SUM(op.quantity) AS quantity, op.price::money::numeric::float8 AS price" +
                    " FROM order_product op" +
                    " JOIN product p on p.id = op.product_id" +
                    " JOIN brand b on b.id = p.brand_id" +
                    " WHERE order_id=?" +
                    " GROUP BY p.id, p.name, b.name, p.weight, op.price::money::numeric::float8" +
                    " ORDER BY p.name";

    private static final EntityMapper<OrderView.ProductInOrderView> mapProductInOrderViewRow = rs ->
            new OrderView.ProductInOrderView(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("brand_name"),
                    rs.getBoolean("weight"),
                    rs.getInt("quantity"),
                    0,
                    rs.getDouble("price"));
    private static final EntitiesMapper<OrderView.ProductInOrderView> mapProductInOrderViewRows = rs -> {
        List<OrderView.ProductInOrderView> list = new ArrayList<>();
        while (rs.next()) {
            list.add(mapProductInOrderViewRow.mapRow(rs));
        }
        return list;
    };

    public static final EntityMapper<OrderProduct> mapOrderProductRow = resultSet ->
            OrderProduct.newBuilder()
                    .setId(resultSet.getInt(ID))
                    .setOrderId(resultSet.getInt(ORDER_ID))
                    .setProductId(resultSet.getInt(PRODUCT_ID))
                    .setQuantity(resultSet.getInt(QUANTITY))
                    .setPrice(resultSet.getDouble(PRICE))
                    .build();

    private static final EntitiesMapper<OrderProduct> mapOrderProductRows = resultSet -> {
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

        try (Connection connection = ConnectionProvider.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ORDER_PRODUCT_VIEW_BY_ORDER_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return mapProductInOrderViewRows.mapList(rs);
            }
        } catch (SQLException e) {
            LOG.error("Error when receive data from ProductInOrderView", e);
            throw new CashMachineException("Error when receive data from ProductInOrderView", e);
        }
    }

    @Override
    public double getSumByOrderId(int orderId) throws CashMachineException {
        double sum = 0;
        try (Connection conn = ConnectionProvider.getInstance().getConnection()) {
            sum = getSumProductsInOrderWithConnection(orderId, conn);
        } catch (SQLException e) {
            LOG.error("Error when calc sum for order id: " + orderId);
            throw new CashMachineException("Error when calc sum for order id: " + orderId, e);
        }
        return sum;
    }

    private double getSumProductsInOrderWithConnection(int orderId, Connection conn) throws CashMachineException {
        double sum = 0;
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_SUM_BY_ORDER_ID)) {
            statement.setInt(1, orderId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    sum = rs.getDouble(1);
                }
            }

        } catch (SQLException e) {
            LOG.error("Error when calc sum for order id: " + orderId);
            throw new CashMachineException("Error when calc sum for order id: " + orderId, e);
        }
        return sum;
    }

    private int getSumQuantityProductInOrder(int orderId, int productId, Connection conn) throws CashMachineException {
        try (PreparedStatement statement = conn.prepareStatement(SQL_SELECT_QUANTITY_FROM_ORDER_PRODUCT_BY_ORDER_ID_AND_PRODUCT_ID)) {
            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when calc quantity");
            throw new CashMachineException("Error when calc quantity", e);
        }
        return 0;
    }

    @Override
    public boolean deleteProductFromOrder(int orderId, int productId) throws CashMachineException {
        boolean result = false;
        Connection conn = null;
        try {
            conn = ConnectionProvider.getInstance().getConnection();
            conn.setAutoCommit(false);
            int newQuantity = getSumQuantityProductInOrder(orderId, productId, conn);
            result = deleteProductFromOrderWithConnection(orderId, productId, conn);
            double newOrderSum = getSumProductsInOrderWithConnection(orderId, conn);
            JdbcOrderDaoImpl jdbcOrderDao = new JdbcOrderDaoImpl();
            Optional<Order> order = jdbcOrderDao.findById(orderId, conn);
            if (!order.isPresent()) {
                LOG.error("Error when transaction.");
                rollbackWhenError(conn);
                throw new CashMachineException("Can not get order with ID: " + orderId);
            }
            order.get().setAmount(newOrderSum);
            jdbcOrderDao.updateOrderWithConnection(order.get(), conn);

            JdbcProductDaoImpl productDao = new JdbcProductDaoImpl();
            Optional<Product> product = productDao.findByIdWithConnection(conn, productId);
            if (!product.isPresent()) {
                rollbackWhenError(conn);
            }
            newQuantity += product.get().getQuantity();
            Product p = product.get();
            p.setQuantity(newQuantity);
            productDao.updateProductWithConnection(conn, p);
            conn.commit();
        } catch (SQLException e) {
            rollbackWhenError(conn);
            LOG.error("Error when delete product from order: orderId=" + orderId + ", productId=" + productId, e);
            throw new CashMachineException("Error when delete product from order: orderId=" + orderId + ", productId=" + productId, e);
        }
        return result;
    }

    private void rollbackWhenError(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LOG.error("Error when rollback ", e1);
            }
        }
    }

    @Override
    public boolean deleteProductFromOrderWithConnection(int orderId, int productId, Connection conn) throws CashMachineException {
        try (PreparedStatement statement = conn.prepareStatement("DELETE FROM order_product WHERE order_id=? AND product_id=?")) {
            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error when delete product from order: orderId=" + orderId + ", productId=" + productId, e);
            throw new CashMachineException("Error when delete product from order: orderId=" + orderId + ", productId=" + productId, e);
        }
        return true;
    }

    @Override
    public boolean deleteAll(Connection conn) throws CashMachineException {
        try (Statement statement = conn.createStatement()){
            return statement.execute("DELETE FROM order_product");
        } catch (SQLException e) {
            LOG.error("Error when delete products of orders", e);
            throw new CashMachineException("Error when delete products of orders", e);
        }
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