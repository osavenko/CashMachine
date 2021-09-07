package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.BrandDao;
import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Brand;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.Brand.ID;
import static com.epam.savenko.cashmachine.dao.Fields.Brand.NAME;

public class JdbcBrandDaoImpl implements BrandDao {

    private static final Logger LOG = Logger.getLogger(JdbcBrandDaoImpl.class.getName());

    private static final String TABLE_NAME = "brand";

    private static final String SQL_INSERT = "INSERT INTO brand (name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE brand SET name=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM brand WHERE id=?";
    private static final String SQL_SELECT_ALL_BRANDS = "SELECT * FROM brand";
    private static final String SQL_SELECT_BRAND_BY_ID = "SELECT * FROM brand WHERE id=?";

    private static final EntityMapper<Brand> mapBrandRow = resultSet ->
            new Brand(resultSet.getInt(ID), resultSet.getString(NAME));

    private static final EntitiesMapper<Brand> mapBrandList = (resultSet) -> {
        List<Brand> brands = new ArrayList<>();
        while (resultSet.next()) {
            brands.add(mapBrandRow.mapRow(resultSet));
        }
        return brands;
    };

    private JdbcEntity<Brand> jdbcEntity;

    public JdbcBrandDaoImpl() {
        jdbcEntity = new JdbcEntity<>(mapBrandList, TABLE_NAME, LOG);
    }

    @Override
    public Optional<Brand> findById(Integer integer) throws CashMachineException {
        return jdbcEntity.findById(integer, SQL_SELECT_BRAND_BY_ID);
    }

    @Override
    public List<Brand> findAll() throws CashMachineException {
        return jdbcEntity.findAll(SQL_SELECT_ALL_BRANDS);
    }

    @Override
    public Optional<Brand> insert(Brand entity) throws CashMachineException {
        int id = jdbcEntity.insert(SQL_INSERT, entity.getName());
        entity.setId(id);
        return Optional.ofNullable(id > 0 ? entity : null);
    }

    @Override
    public boolean update(Brand entity) throws CashMachineException {
        return jdbcEntity.update(SQL_UPDATE, entity.getId(), entity.getName());
    }

    @Override
    public boolean delete(int id) throws CashMachineException {
        return jdbcEntity.delete(SQL_DELETE, id);
    }
}