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

    public static Builder newBuilder() {
        return new OrderProduct().new Builder();
    }

    public class Builder {
        public Builder() {
        }

        public Builder setId(int id) {
            OrderProduct.this.id = id;
            return this;
        }

        public Builder setOrderId(int id) {
            OrderProduct.this.orderId = id;
            return this;
        }

        public Builder setProductId(int id) {
            OrderProduct.this.productId = id;
            return this;
        }

        public Builder setQuantity(int quantity) {
            OrderProduct.this.quantity = quantity;
            return this;
        }

        public Builder setPrice(double price) {
            OrderProduct.this.price = price;
            return this;
        }

        public OrderProduct build() {
            return OrderProduct.this;
        }
    }
}
