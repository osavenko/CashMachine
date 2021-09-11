package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcCountEntity {

    private static final Logger LOG = Logger.getLogger(JdbcCountEntity.class.getName());

    public int getCount(String sql, String tableName) throws CashMachineException {
        int count = 0;
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             Statement statement = con.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when counting rows in " + tableName, e);
            throw new CashMachineException("Error when counting rows in " + tableName, e);
        }
        return count;
    }
}
