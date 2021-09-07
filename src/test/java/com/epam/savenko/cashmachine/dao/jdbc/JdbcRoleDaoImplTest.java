package com.epam.savenko.cashmachine.dao.jdbc;

import com.epam.savenko.cashmachine.dao.RoleDao;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class JdbcRoleDaoImplTest extends Assertions {

    @BeforeEach
    void setUp() {
    }

    @Test
    void findById() {
        assertThrows(CashMachineException.class, () -> {
            RoleDao roleDao = new JdbcRoleDaoImpl();
            roleDao.findById(1);
        });
    }

    @Test
    void delete() {
        assertThrows(CashMachineException.class, () -> {
            RoleDao roleDao = new JdbcRoleDaoImpl();
            roleDao.delete(1);
        });
    }
}