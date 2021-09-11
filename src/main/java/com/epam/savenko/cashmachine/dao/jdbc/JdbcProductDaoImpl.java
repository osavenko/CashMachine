package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.ConnectionProvider;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.ProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.util.ErrorMessage;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Product;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.Product.*;

public class JdbcProductDaoImpl implements ProductDao {
    private static final Logger LOG = Logger.getLogger(JdbcProductDaoImpl.class.getName());

    private static final String TABLE_NAME = "product";

    private static final String SQL_INSERT = "INSERT INTO product (name, brand_id, price, quantity, weight) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE product SET name =?, brand_id=?, price=?, quantity=?, weight=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM product WHERE id=?";
    private static final String SQL_SELECT_ALL_PRODUCTS = "SELECT * FROM product ORDER BY id";
    private static final String SQL_PRODUCT_COUNT = "SELECT count(*) FROM product";
    private static final String SQL_SELECT_PRODUCT_BY_ID = "SELECT * FROM product WHERE id=?";
    private static final String SQL_SELECT_PRODUCT_BY_PAGES = "SELECT id, name, quantity, price::money::numeric::float8, brand_id, weight FROM product ORDER BY id LIMIT ? OFFSET ?";

    private static final EntityMapper<Product> mapProductRow = resultSet ->
            Product.newBuilder()
                    .setId(resultSet.getInt(ID))
                    .setName(resultSet.getString(NAME))
                    .setBrandId(resultSet.getInt(BRAND_ID))
                    .setPrice(resultSet.getDouble(PRICE))
                    .setQuantity(resultSet.getInt(QUANTITY))
                    .setWeight(resultSet.getBoolean(WEIGHT))
                    .build();

    public static final EntitiesMapper<Product> mapProductRows = resultSet -> {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(mapProductRow.mapRow(resultSet));
        }
        return products;
    };

    private JdbcEntity<Product> jdbcEntity;

    public JdbcProductDaoImpl() {
        jdbcEntity = new JdbcEntity<>(mapProductRows, TABLE_NAME, LOG);
    }

    @Override
    public Optional<Product> findById(Integer integer) throws CashMachineException {
        return jdbcEntity.findById(integer, SQL_SELECT_PRODUCT_BY_ID);
    }

    @Override
    public List<Product> findAll() throws CashMachineException {
        return jdbcEntity.findAll(SQL_SELECT_ALL_PRODUCTS);
    }

    @Override
    public Optional<Product> insert(Product entity) throws CashMachineException {
        int id = insertProduct(entity);
        entity.setId(id);
        return Optional.ofNullable(id > 0 ? entity : null);
    }

    @Override
    public int getCount() throws CashMachineException {
        return new JdbcCountEntity().getCount(SQL_PRODUCT_COUNT, TABLE_NAME);
    }

    @Override
    public List<Product> findPage(int rows, int offset) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_SELECT_PRODUCT_BY_PAGES)) {
            statement.setInt(1, rows);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                return mapProductRows.mapList(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error pagination database", e);
            throw new CashMachineException("Error pagination database", e);
        }
    }

    private int insertProduct(Product product) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getBrandId());
            statement.setBigDecimal(3, BigDecimal.valueOf(product.getPrice()));
            statement.setInt(4, product.getQuantity());
            statement.setBoolean(5, product.isWeight());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException | CashMachineException e) {
            StringBuilder sb = new StringBuilder(ErrorMessage.getInsert(TABLE_NAME))
                    .append(product);
            LOG.error(sb, e);
            throw new CashMachineException(sb.toString(), e);
        }
        return -1;
    }

    @Override
    public boolean update(Product entity) throws CashMachineException {
        try (Connection con = ConnectionProvider.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getBrandId());
            statement.setBigDecimal(3, BigDecimal.valueOf(entity.getPrice()));
            statement.setInt(4, entity.getQuantity());
            statement.setBoolean(5, entity.isWeight());
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
}