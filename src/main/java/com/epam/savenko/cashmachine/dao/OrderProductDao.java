package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.OrderProduct;
import com.epam.savenko.cashmachine.model.view.OrderView.ProductInOrderView;

import java.util.List;

public interface OrderProductDao extends GeneralDao<OrderProduct,Integer>{
    List<ProductInOrderView> getProductInOrderViewById(int id) throws CashMachineException;
}
