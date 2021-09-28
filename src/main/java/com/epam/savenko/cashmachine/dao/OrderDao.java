package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Order;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface OrderDao extends GeneralDao<Order, Integer>, Count {
    double getSumCard() throws CashMachineException;

    double getSumCash() throws CashMachineException;

    Optional<Order> findById(int id, Connection conn) throws CashMachineException;

    List<String> getUsersFullNameInOrders() throws CashMachineException;
}
