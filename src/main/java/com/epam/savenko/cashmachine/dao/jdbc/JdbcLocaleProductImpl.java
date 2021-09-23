package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.LocaleProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.LocaleProduct;
import com.epam.savenko.cashmachine.model.view.ProductDescriptionView;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.LocaleProduct.*;

public class JdbcLocaleProductImpl implements LocaleProductDao {

    private static final Logger LOG = Logger.getLogger(JdbcLocaleProductImpl.class.getName());

    private static final String TABLE_NAME = "locale_product";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (locale_id, description, product_id) VALUES (?,?,?)";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET locale_id=?, description=?, product_id=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SQL_SELECT_ALL_LOCALE_PRODUCTS = "SELECT * FROM " + TABLE_NAME;
    private static final String SQL_SELECT_DESCRIPTIONS_ALL_LOCALE_PRODUCT_BY_ID_PRODUCT = "SELECT l.name, lp.description FROM locale_product AS lp\n" +
            "JOIN locale l on l.id = lp.locale_id\n" +
            "JOIN product p on p.id = lp.product_id\n" +
            "WHERE p.id=?";
    private static final String SQL_SELECT_LOCALE_PRODUCT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SQL_SELECT_DESCRIPTION_TO_ID_PRODUCT_BY_LOCALE =
            "SELECT id, product_id, locale_id, description FROM locale_product WHERE product_id=? AND locale_id=?";

    private static final EntityMapper<LocaleProduct> mapLocaleProductRow = resultSet -> new LocaleProduct(resultSet.getInt(ID),
            resultSet.getInt(LOCALE_ID),
            resultSet.getInt(PRODUCT_ID),
            resultSet.getString(DESCRIPTION));

    private static final EntitiesMapper<LocaleProduct> mapLocaleProductRows = resultSet -> {
        List<LocaleProduct> localeProducts = new ArrayList<>();
        while (resultSet.next()) {
            localeProducts.add(mapLocaleProductRow.mapRow(resultSet));
        }
        return localeProducts;
    };

    JdbcEntity<LocaleProduct> jdbcEntity;

    public JdbcLocaleProductImpl() {
        jdbcEntity = new JdbcEntity<>(mapLocaleProductRows, TABLE_NAME, LOG);
    }

    @Override
    public Optional<LocaleProduct> findById(Integer integer) throws CashMachineException {
        return jdbcEntity.findById(integer, SQL_SELECT_LOCALE_PRODUCT_BY_ID);
    }

    @Override
    public List<LocaleProduct> findAll() throws CashMachineException {
        return jdbcEntity.findAll(SQL_SELECT_ALL_LOCALE_PRODUCTS);
    }

    @Override
    public Optional<LocaleProduct> insert(LocaleProduct entity) throws CashMachineException {
        int id = insertLocaleProduct(entity);
        entity.setId(id);
        return Optional.ofNullable(id > 0 ? entity : null);
    }

    private int insertLocaleProduct(LocaleProduct lp) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, lp.getLocateId());
            statement.setString(2, lp.getDescription());
            statement.setInt(3, lp.getProductId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getInsert(TABLE_NAME))
                    .append(lp);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return -1;
    }

    @Override
    public boolean update(LocaleProduct entity) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_UPDATE)) {
            statement.setInt(1, entity.getLocateId());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getProductId());
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
    public Optional<LocaleProduct> findDescriptionProductByLocale(int idProduct, int localeId) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_SELECT_DESCRIPTION_TO_ID_PRODUCT_BY_LOCALE)) {
            statement.setInt(1, idProduct);
            statement.setInt(2, localeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.ofNullable(mapLocaleProductRow.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(ErrorMessage.getReceiveById(TABLE_NAME));
            throw new CashMachineException("ErrorMessage.getReceiveById(TABLE_NAME) id, localeId" + idProduct + ":" + localeId, e);
        }
        return Optional.empty();
    }

    @Override
    public List<ProductDescriptionView> getAllDescriptionViewForProductById(int idProduct) throws CashMachineException {
        List<ProductDescriptionView> pView = new ArrayList<>();
        try (Connection conn = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_SELECT_DESCRIPTIONS_ALL_LOCALE_PRODUCT_BY_ID_PRODUCT)) {
            statement.setInt(1, idProduct);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    pView.add(new ProductDescriptionView(rs.getString(1), rs.getString(2)));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when receive all description for product with id: " + idProduct, e);
        }
        return pView;
    }
}