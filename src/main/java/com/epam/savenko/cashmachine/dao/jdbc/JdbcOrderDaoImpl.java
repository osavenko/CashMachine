package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.OrderDao;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Order;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.Order.*;

public class JdbcOrderDaoImpl implements OrderDao {

    private static final Logger LOG = Logger.getLogger(JdbcOrderDaoImpl.class.getName());

    private static final String TABLE_NAME = "order";

    private static final String SQL_INSERT = "INSERT INTO order (user_id, closed, amount) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE order SET user_id=?, closed=?, amount=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM order WHERE id=?";
    private static final String SQL_SELECT_ALL_ORDERS = "SELECT * FROM order";
    private static final String SQL_SELECT_ORDER_COUNTS = "SELECT count(*) FROM order";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT * FROM order WHERE id=?";

    private static final EntityMapper<Order> mapOrderRow = resultSet ->
            Order.newOrder()
                    .setId(resultSet.getInt(ID))
                    .setUserId(resultSet.getInt(USER_ID))
                    .setState(resultSet.getBoolean(CLOSED))
                    .setAmount(resultSet.getDouble(AMOUNT))
                    .build();

    public static final EntitiesMapper<Order> mapOrderRows = resultSet -> {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            orders.add(mapOrderRow.mapRow(resultSet));
        }
        return orders;
    };

    private JdbcEntity<Order> jdbcEntity;

    public JdbcOrderDaoImpl() {
        jdbcEntity = new JdbcEntity<>(mapOrderRows, TABLE_NAME, LOG);
    }

    @Override
    public Optional<Order> findById(Integer integer) throws CashMachineException {
        return jdbcEntity.findById(integer, SQL_SELECT_ORDER_BY_ID);
    }

    @Override
    public List<Order> findAll() throws CashMachineException {
        return jdbcEntity.findAll(SQL_SELECT_ALL_ORDERS);
    }

    @Override
    public Optional<Order> insert(Order entity) throws CashMachineException {
        int id = insertOrder(entity);
        entity.setId(id);
        return Optional.ofNullable(id > 0 ? entity : null);
    }

    private int insertOrder(Order order) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getUserId());
            statement.setBoolean(2, order.isClosed());
            statement.setDouble(3, order.getAmount());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getInsert(TABLE_NAME))
                    .append(order);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return -1;
    }

    @Override
    public boolean update(Order entity) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_UPDATE)) {
            statement.setInt(1, entity.getUserId());
            statement.setBoolean(2, entity.isClosed());
            statement.setDouble(3, entity.getAmount());
            statement.setInt(4, entity.getId());
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

    @Override
    public int getCount() throws CashMachineException {
        return new JdbcCountEntity().getCount(SQL_SELECT_ORDER_COUNTS, TABLE_NAME);
    }
}