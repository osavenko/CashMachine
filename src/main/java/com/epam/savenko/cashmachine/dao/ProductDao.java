package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Product;

import java.util.List;

public interface ProductDao extends GeneralDao<Product, Integer>{
    int productCount() throws CashMachineException;
    List<Product> findPage(int rows, int offset) throws CashMachineException;
}
