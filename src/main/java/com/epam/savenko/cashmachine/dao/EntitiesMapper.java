package com.epam.savenko.cashmachine.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface EntitiesMapper<E> {
    List<E> mapList(ResultSet rs) throws SQLException;
}
