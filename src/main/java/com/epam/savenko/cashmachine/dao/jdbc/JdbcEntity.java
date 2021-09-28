package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JdbcEntity<T> {

    private final EntitiesMapper<T> mapRows;
    private final String tableName;
    private final Logger LOG;

    public JdbcEntity(EntitiesMapper<T> mapRows, String tableName, Logger LOG) {
        this.mapRows = mapRows;
        this.tableName = tableName;
        this.LOG = LOG;
    }

    public Optional<T> findById(Integer integer, String sql) throws CashMachineException {
        return Optional.ofNullable(findEntityById(integer, sql));
    }

    public List<T> findAll(String sql) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            return getEntityByStatement(statement);
        } catch (SQLException | CashMachineException e) {
            LOG.error(ErrorMessage.getReceiveAll(tableName), e);
            throw new CashMachineException(ErrorMessage.getReceiveAll(tableName), e);
        }
    }

    private T findEntityById(int id, String sql) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, id);
            List<T> entities = getEntityByStatement(statement);
            if (!entities.isEmpty()) {
                return entities.get(0);
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getReceiveById(tableName))
                    .append(id);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return null;
    }

    private List<T> getEntityByStatement(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();
        return mapRows.mapList(rs);
    }

    public boolean delete(String sql, int id) throws CashMachineException {
        boolean result = false;
        try(Connection connection = ConnectionProvider.getInstance().getConnection()){
            result = deleteWithConnection(connection,sql,id);
        }catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getDelete(tableName))
                    .append(id);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return result;
    }

    public boolean deleteWithConnection(Connection conn, String sql, int id) throws CashMachineException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getDelete(tableName))
                    .append(id);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return true;
    }

    public boolean update(String sql, int id, String name) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getUpdate(tableName))
                    .append(id);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return true;
    }

    public int insert(String sql, String name) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getInsert(tableName))
                    .append(name);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return -1;
    }


}
