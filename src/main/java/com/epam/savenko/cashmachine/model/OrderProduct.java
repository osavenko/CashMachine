package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

public class OrderProduct implements Serializable {

    private static final long serialVersionUID = -445594252408032387L;

    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double price;

    public OrderProduct() {
    }

    public OrderProduct(int orderId, int productId, int quantity, double price) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderProduct(Order order, Product product, int quantity, double price) {
        this(order.getId(), product.getId(), quantity, price);
    }

    public OrderProduct(int id, int orderId, int productId, int quantity, double price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderProduct(int id, Order order, Product product, int quantity, double price) {
        this(id, order.getId(), product.getId(), quantity, price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderId(Order order) {
        this.orderId = order.getId();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductId(Product product) {
        this.productId = product.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderProduct that = (OrderProduct) o;
        return id == that.id && orderId == that.orderId && productId == that.productId && quantity == that.quantity && Double.compare(that.price, price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, productId, quantity, price);
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
