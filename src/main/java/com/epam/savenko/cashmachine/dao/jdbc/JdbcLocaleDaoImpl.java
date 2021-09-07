package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.LocaleDao;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Locale;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.Locale.*;


public class JdbcLocaleDaoImpl implements LocaleDao {

    private static final Logger LOG = Logger.getLogger(JdbcLocaleDaoImpl.class.getName());

    private static final String TABLE_NAME = "locate";

    private static final String SQL_INSERT = "INSERT INTO locale (name, description) VALUES (?,?)";
    private static final String SQL_UPDATE = "UPDATE locale SET name=?, description=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM locale WHERE id=?";
    private static final String SQL_SELECT_ALL_LOCALES = "SELECT * FROM locale";
    private static final String SQL_SELECT_LOCALE_BY_ID = "SELECT * FROM locale WHERE id=?";
    private static final String SQL_SELECT_LOCALE_BY_NAME = "SELECT * FROM locale WHERE name=?";

    private static final EntityMapper<Locale> mapLocaleRow = resultSet -> new Locale(resultSet.getInt(ID),
            resultSet.getString(NAME),
            resultSet.getString(DESCRIPTION));

    private static final EntitiesMapper<Locale> mapLocateRows = resultSet -> {
        List<Locale> locales = new ArrayList<>();
        while (resultSet.next()) {
            locales.add(mapLocaleRow.mapRow(resultSet));
        }
        return locales;
    };

    private JdbcEntity<Locale> jdbcEntity;

    public JdbcLocaleDaoImpl() {
        jdbcEntity = new JdbcEntity<>(mapLocateRows, TABLE_NAME, LOG);
    }

    @Override
    public Optional<Locale> findById(Integer integer) throws CashMachineException {
        return jdbcEntity.findById(integer, SQL_SELECT_LOCALE_BY_ID);
    }

    @Override
    public List<Locale> findAll() throws CashMachineException {
        return jdbcEntity.findAll(SQL_SELECT_ALL_LOCALES);
    }

    @Override
    public Optional<Locale> insert(Locale entity) throws CashMachineException {
        int id = insertLocale(entity);
        entity.setId(id);
        return Optional.ofNullable(id > 0 ? entity : null);
    }

    private int insertLocale(Locale locale) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, locale.getName());
            statement.setString(2, locale.getDescription());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getInsert(TABLE_NAME))
                    .append(locale);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return -1;
    }

    @Override
    public boolean update(Locale entity) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
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
    public Optional<Locale> findByName(String name) throws CashMachineException {
        Locale locale = null;
        try (Connection con = ConnectionProvider.getInstance().getConnection();
        PreparedStatement statement = con.prepareStatement(SQL_SELECT_LOCALE_BY_NAME)){
            statement.setString(1, name);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    locale = mapLocaleRow.mapRow(resultSet);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getCheckLocaleByName())
                    .append(name);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return Optional.ofNullable(locale);
    }
}
