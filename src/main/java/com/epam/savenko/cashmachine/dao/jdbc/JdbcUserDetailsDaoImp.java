package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.UserDetailsDao;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.UserDetails;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.UserDetails.*;


public class JdbcUserDetailsDaoImp implements UserDetailsDao {

    private static final Logger LOG = Logger.getLogger(JdbcLocaleProductImpl.class.getName());

    private static final String TABLE_NAME = "user_details";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (fullname, user_id) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET fullname=?, user_id=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SQL_SELECT_ALL_USER_DETAILS = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SELECT_USER_DETAIL_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SQL_SELECT_USER_DETAIL_BY_USER_ID = "SELECT * FROM " + TABLE_NAME + " WHERE user_id=?";

    private static final EntityMapper<UserDetails> mapUserDetailRow = resultSet -> new UserDetails(resultSet.getInt(ID),
            resultSet.getString(FULLNAME),
            resultSet.getInt(USER_ID));

    private static final EntitiesMapper<UserDetails> mapUserDetailsRows = resultSet -> {
        List<UserDetails> userDetails = new ArrayList<>();
        while (resultSet.next()) {
            userDetails.add(mapUserDetailRow.mapRow(resultSet));
        }
        return userDetails;
    };

    private final JdbcEntity<UserDetails> jdbcEntity;

    public JdbcUserDetailsDaoImp() {
        jdbcEntity = new JdbcEntity<>(mapUserDetailsRows, TABLE_NAME, LOG);
    }

    @Override
    public Optional<UserDetails> findById(Integer integer) throws CashMachineException {
        return jdbcEntity.findById(integer, SQL_SELECT_USER_DETAIL_BY_ID);
    }

    @Override
    public List<UserDetails> findAll() throws CashMachineException {
        return jdbcEntity.findAll(SQL_SELECT_ALL_USER_DETAILS);
    }

    @Override
    public Optional<UserDetails> insert(UserDetails entity) throws CashMachineException {
        int id = insertUserDetails(entity);
        entity.setId(id);
        return Optional.ofNullable(id > 0 ? entity : null);
    }

    private int insertUserDetails(UserDetails userDetails) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userDetails.getFullName());
            statement.setInt(2, userDetails.getUserId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getInsert(TABLE_NAME))
                    .append(userDetails);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return -1;
    }

    @Override
    public boolean update(UserDetails entity) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getFullName());
            statement.setInt(2, entity.getUserId());
            statement.setInt(3, entity.getId());
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
    public Optional<UserDetails> findByUserId(Integer key) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_SELECT_USER_DETAIL_BY_USER_ID)) {
            statement.setInt(1, key);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(mapUserDetailRow.mapRow(resultSet));
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getReceiveByUserId(TABLE_NAME))
                    .append(key);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return Optional.empty();
    }
}
