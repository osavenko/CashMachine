package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {

    private static ConnectionProvider instant;

    private DataSource dataSource;

    public static synchronized ConnectionProvider getInstance() throws CashMachineException {
        if (instant == null) {
            instant = new ConnectionProvider();
        }
        return instant;
    }

    private ConnectionProvider() throws CashMachineException {
        try {
            InitialContext cxt = new InitialContext();
            dataSource = (DataSource) cxt.lookup("java:/comp/env/jdbc/cashbox");
        } catch (NamingException ex) {
            throw new CashMachineException("Cannot init ConnectionProvider", ex);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
