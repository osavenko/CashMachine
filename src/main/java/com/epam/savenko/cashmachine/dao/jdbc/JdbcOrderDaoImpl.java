package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.OrderDao;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Order;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.Order.*;

public class JdbcOrderDaoImpl implements OrderDao {

    private static final Logger LOG = Logger.getLogger(JdbcOrderDaoImpl.class.getName());

    private static final String TABLE_NAME = "\"order\"";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (user_id, closed, amount) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET user_id=?, closed=?, amount=?, cash=?, closed_datetime=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SQL_SELECT_ALL_ORDERS = "SELECT id, amount::money::numeric::float8, user_id, closed, order_datetime, closed_datetime,cash FROM " + TABLE_NAME;
    private static final String SQL_SELECT_SUM_CASH = "SELECT SUM(amount::money::numeric::float8) FROM " + TABLE_NAME + " WHERE closed=true AND cash=true";
    private static final String SQL_SELECT_SUM_CARD = "SELECT SUM(amount::money::numeric::float8) FROM " + TABLE_NAME + " WHERE closed=true AND cash=true";
    private static final String SQL_SELECT_ORDER_COUNTS = "SELECT count(*) FROM " + TABLE_NAME + " WHERE closed=true";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT id, amount::money::numeric::float8, user_id, closed, order_datetime, closed_datetime, cash FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SQL_SELECT_USERS_CLOSED_ORDER = "SELECT ud.fullname FROM \"order\" o" +
            " JOIN \"user\" u on u.id = o.user_id" +
            " JOIN user_details ud on o.user_id = ud.user_id" +
            " WHERE o.closed=true" +
            " GROUP BY ud.fullname";

    private static final EntityMapper<Order> mapOrderRow = resultSet ->
            Order.newOrder()
                    .setId(resultSet.getInt(ID))
                    .setUserId(resultSet.getInt(USER_ID))
                    .setState(resultSet.getBoolean(CLOSED))
                    .setAmount(resultSet.getDouble(AMOUNT))
                    .setCash(resultSet.getBoolean(CASH))
                    .setOrderDateTime(resultSet.getTimestamp(ORDER_DATETIME))
                    .setClosedDateTime(resultSet.getTimestamp(CLOSED_DATETIME))
                    .build();

    public static final EntitiesMapper<Order> mapOrderRows = resultSet -> {
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            orders.add(mapOrderRow.mapRow(resultSet));
        }
        return orders;
    };

    private final JdbcEntity<Order> jdbcEntity;

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
        return findById(id);
    }

    private int insertOrder(Order order) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getUserId());
            statement.setBoolean(2, order.isClosed());
            statement.setBigDecimal(3, BigDecimal.valueOf(order.getAmount()));
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
            statement.setBigDecimal(3, BigDecimal.valueOf(entity.getAmount()));
            statement.setBoolean(4, entity.isCash());
            statement.setTimestamp(5, entity.getClosedDateTime());
            statement.setInt(6, entity.getId());
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

    @Override
    public double getSumCard() throws CashMachineException {
        return getSum(SQL_SELECT_SUM_CARD);
    }

    @Override
    public double getSumCash() throws CashMachineException {
        return getSum(SQL_SELECT_SUM_CASH);
    }

    @Override
    public List<String> getUsersFullNameInOrders() throws CashMachineException {
        List<String> users = new ArrayList<>();
        try (Connection conn = ConnectionProvider.getInstance().getConnection();
             Statement statement = conn.createStatement()) {
            try (ResultSet rs = statement.executeQuery(SQL_SELECT_USERS_CLOSED_ORDER)) {
                while (rs.next()) {
                    users.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private double getSum(String sql) throws CashMachineException {
        double sum = 0;
        try (Connection conn = ConnectionProvider.getInstance().getConnection();
             Statement statement = conn.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    sum = resultSet.getDouble(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when calculate sum ", e);
        }
        return sum;
    }
}