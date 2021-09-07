package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.EntitiesMapper;
import com.epam.savenko.cashmachine.dao.EntityMapper;
import com.epam.savenko.cashmachine.dao.RoleDao;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Role;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.dao.Fields.Role.ID;
import static com.epam.savenko.cashmachine.dao.Fields.Role.NAME;

public class JdbcRoleDaoImpl implements RoleDao {

    private static final Logger LOG = Logger.getLogger(JdbcRoleDaoImpl.class.getName());

    private static final String TABLE_NAME = "role";

    private static final String SQL_INSERT = "INSERT INTO \"role\" (name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE \"role\" SET name=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM \"role\" WHERE id=?";
    private static final String SQL_SELECT_ALL_ROLES = "SELECT * FROM \"role\"";
    private static final String SQL_SELECT_ROLE_BY_ID = "SELECT * FROM \"role\" WHERE id=?";

    private static final EntityMapper<Role> mapRoleRow = resultSet ->
            new Role(resultSet.getInt(ID), resultSet.getString(NAME));

    private static final EntitiesMapper<Role> mapRoleRows = resultSet -> {
        List<Role> roles = new ArrayList<>();
        while (resultSet.next()) {
            roles.add(mapRoleRow.mapRow(resultSet));
        }
        return roles;
    };

    private JdbcEntity<Role> jdbcEntity;

    public JdbcRoleDaoImpl() {
        this.jdbcEntity = new JdbcEntity<>(mapRoleRows, TABLE_NAME, LOG);
    }

    @Override
    public Optional<Role> findById(Integer integer) throws CashMachineException {
        return jdbcEntity.findById(integer, SQL_SELECT_ROLE_BY_ID);
    }

    @Override
    public List<Role> findAll() throws CashMachineException {
        return jdbcEntity.findAll(SQL_SELECT_ALL_ROLES);
    }

    @Override
    public Optional<Role> insert(Role entity) throws CashMachineException {
        int id = jdbcEntity.insert(SQL_INSERT, entity.getName());
        entity.setId(id);
        return Optional.ofNullable(id > 0 ? entity : null);
    }

    @Override
    public boolean update(Role entity) throws CashMachineException {
        return jdbcEntity.update(SQL_UPDATE, entity.getId(), entity.getName());
    }

    @Override
    public boolean delete(int id) throws CashMachineException {
        return jdbcEntity.delete(SQL_DELETE, id);
    }
}