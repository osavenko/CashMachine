package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.AppPropertiesDao;
import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.AppProperties;
import org.apache.log4j.Logger;

import javax.swing.text.ChangedCharSetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcAppPropertiesDao implements AppPropertiesDao {

    private static final Logger LOG = Logger.getLogger(JdbcAppPropertiesDao.class.getName());
    public static final String TABLE_NAME = "appproperties";

    public static final String SQL_SELECT_BY_NAME = "SELECT id, value FROM appproperties WHERE name=?";

    @Override
    public Optional<AppProperties> getByName(String name) throws CashMachineException {

        try (Connection conn = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_SELECT_BY_NAME)) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    AppProperties appProperties = new AppProperties(rs.getInt(1), name, rs.getString(2));
                    return Optional.ofNullable(appProperties);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when receive property: " + name);
        }
        return Optional.empty();
    }

    @Override
    public double getTax() throws CashMachineException {
        Optional<AppProperties> tax = getByName("tax");
        double dTax = 0.0;
        if (tax.isPresent()) {
            try {
                dTax = Double.parseDouble(tax.get().getValue());
            } catch (NumberFormatException e) {
                LOG.error("Error when tax convert ", e);
            }
        }
        return dTax;
    }
}
