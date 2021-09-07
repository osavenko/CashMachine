package com.epam.savenko.cashmachine.model.view;

import com.epam.savenko.cashmachine.model.Order;
import com.epam.savenko.cashmachine.model.OrderProduct;

import java.io.Serializable;
import java.util.List;

public class OrderView implements Serializable {

    private static final long serialVersionUID = 2489483659859659232L;

    private Order order;
    private List<OrderProduct> products;


    public OrderView() {
    }

    public OrderView(Order order) {
        this.order = order;
    }

    public OrderView(Order order, List<OrderProduct> products) {
        this.order = order;
        this.products = products;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }
}
