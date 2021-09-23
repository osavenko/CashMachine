package com.epam.savenko.cashmachine.model.view;

import com.epam.savenko.cashmachine.model.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderView implements Serializable {

    private static final long serialVersionUID = 2489483659859659232L;

    private Order order;

    private List<ProductInOrderView> productInOrderViewList;

    public OrderView() {
    }

    public OrderView(Order order) {
        this.order = order;
        productInOrderViewList = new ArrayList<>();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<ProductInOrderView> getProductInOrderViewList() {
        return productInOrderViewList;
    }

    public void setProductInOrderViewList(List<ProductInOrderView> productInOrderViewList) {
        this.productInOrderViewList = productInOrderViewList;
    }

    public static class ProductInOrderView {
        private int id;
        private String name;
        private String brandName;
        private boolean type;
        private int quantity;
        private int oldQuantity;
        private double price;

        public ProductInOrderView(int id, String name, String brandName, boolean type, int quantity, int oldQuantity, double price) {
            this.id = id;
            this.name = name;
            this.brandName = brandName;
            this.type = type;
            this.quantity = quantity;
            this.oldQuantity = oldQuantity;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getBrandName() {
            return brandName;
        }

        public boolean isWeight() {
            return type;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getOldQuantity() {
            return oldQuantity;
        }

        public double getPrice() {
            return price;
        }
    }
}
