package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Product;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ProductDao extends GeneralDao<Product, Integer>, Count {
    List<Product> findPage(int rows, int offset) throws CashMachineException;
    List<Product> findSearch(int code, String name, int rows, int offset) throws CashMachineException;
    int getCountWhenSearch(int code, String name) throws CashMachineException;

    boolean changeQuantityProduct(int id, int quantity) throws CashMachineException;
    boolean updateProductWithConnection(Connection conn, Product product) throws CashMachineException;
    Optional<Product> findByIdWithConnection(Connection conn, int id) throws CashMachineException;
}
