package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.OrderProduct;
import com.epam.savenko.cashmachine.model.view.OrderView.ProductInOrderView;

import java.sql.Connection;
import java.util.List;

public interface OrderProductDao extends GeneralDao<OrderProduct, Integer> {
    List<ProductInOrderView> getProductInOrderViewById(int id) throws CashMachineException;

    double getSumByOrderId(int orderId) throws CashMachineException;
    boolean deleteProductFromOrder(int orderId, int productId) throws CashMachineException;
    boolean deleteProductFromOrderWithConnection(int orderId, int productId, Connection conn) throws CashMachineException;
    boolean deleteAll(Connection conn) throws CashMachineException;

}
