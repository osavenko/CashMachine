package com.epam.savenko.cashmachine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface EntityMapper<E> {
    E mapRow(ResultSet rs) throws SQLException;
}
