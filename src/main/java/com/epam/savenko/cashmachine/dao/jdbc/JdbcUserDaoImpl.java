package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.UserDao;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.validation.UserValidator;
import com.epam.savenko.cashmachine.validation.Validator;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.User.*;

public class JdbcUserDaoImpl implements UserDao {

    private static final Logger LOG = Logger.getLogger(JdbcUserDaoImpl.class.getName());

    private static final String TABLE_NAME = "user";

    private static final String SQL_INSERT = "INSERT INTO \"user\" (name, role_id, activated, locale_id) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE \"user\" SET name=?, role_id=?, activated=?,locale_id=? WHERE id=?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE \"user\" SET hash=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM \"user\" WHERE id=? ";
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM \"user\"";
    private static final String SQL_SELECT_USERS_BY_LOGIN_AND_HASH = "SELECT * FROM \"user\" WHERE name=? AND hash=? AND activated='true'";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM \"user\" WHERE id=? ";

    private static final EntityMapper<User> mapUserRow = resultSet ->
            new User(resultSet.getInt(ID)
                    , resultSet.getString(NAME)
                    , resultSet.getInt(ROLE_ID)
                    , resultSet.getInt(LOCATE_ID)
                    , resultSet.getBoolean(ACTIVATED));

    private static final EntitiesMapper<User> mapUserList = (resultSet) -> {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(mapUserRow.mapRow(resultSet));
        }
        return users;
    };
    private final JdbcEntity<User> jdbcEntity;

    public JdbcUserDaoImpl() {
        jdbcEntity = new JdbcEntity<>(mapUserList, TABLE_NAME, LOG);
    }

    @Override
    public Optional<User> findById(Integer integer) throws CashMachineException {
        return jdbcEntity.findById(integer, SQL_SELECT_USER_BY_ID);
    }

    @Override
    public List<User> findAll() throws CashMachineException {
        return jdbcEntity.findAll(SQL_SELECT_ALL_USERS);
    }

    @Override
    public Optional<User> insert(User entity) throws CashMachineException {
        validate(entity);
        int id = insertUser(entity);
        entity.setId(id);
        return Optional.ofNullable(id > 0 ? entity : null);
    }

    private void validate(User entity) throws CashMachineException {
        Validator uValidator = new UserValidator(entity);
        if(uValidator.validate()){
            LOG.error("Error user invalid");
            throw new CashMachineException("Error user invalid");
        }
    }

    private int insertUser(User user) throws CashMachineException {
        validate(user);
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getRoleId());
            statement.setBoolean(3, user.isActivated());
            statement.setInt(4, user.getLocaleId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getInsert(TABLE_NAME))
                    .append(user);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return -1;
    }

    @Override
    public boolean update(User entity) throws CashMachineException {
        validate(entity);
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getRoleId());
            statement.setBoolean(3, entity.isActivated());
            statement.setInt(4, entity.getLocaleId());
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

    @Override
    public boolean setPassword(User user, String hash) throws CashMachineException {
        try (Connection connection = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            statement.setString(1, hash);
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getSetPassword())
                    .append(user);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return true;
    }

    @Override
    public Optional<User> check(String login, String hash) throws CashMachineException {
        User user = null;
        try (Connection connection = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN_AND_HASH)) {
            statement.setString(1, login);
            statement.setString(2, hash);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = mapUserRow.mapRow(resultSet);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getCheckUser())
                    .append(login);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return Optional.ofNullable(user);
    }
}