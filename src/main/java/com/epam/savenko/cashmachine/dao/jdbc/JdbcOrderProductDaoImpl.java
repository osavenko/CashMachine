package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.*;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.OrderProduct;
import org.apache.log4j.Logger;

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
            statement.setDouble(4, orderProduct.getPrice());
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