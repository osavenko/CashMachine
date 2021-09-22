package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Order implements Serializable {

    private static final long serialVersionUID = -1948123483814564253L;

    private int id;
    private int userId;
    private boolean closed;
    private double amount;
    private boolean cash;

    private Timestamp orderDateTime;
    private Timestamp closedDateTime;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserId(User user) {
        this.userId = user.getId();
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isCash() {
        return cash;
    }

    public void setCash(boolean cash) {
        this.cash = cash;
    }

    public Timestamp getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(Timestamp orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Timestamp getClosedDateTime() {
        return closedDateTime;
    }

    public void setClosedDateTime(Timestamp closedDateTime) {
        this.closedDateTime = closedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && userId == order.userId && closed == order.closed && Double.compare(order.amount, amount) == 0 && cash == order.cash && orderDateTime.equals(order.orderDateTime) && Objects.equals(closedDateTime, order.closedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, closed, amount, cash, orderDateTime, closedDateTime);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", closed=" + closed +
                ", amount=" + amount +
                ", cash=" + cash +
                ", orderDateTime=" + orderDateTime +
                ", closedDateTime=" + closedDateTime +
                '}';
    }

    public static Builder newOrder() {
        return new Order().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setId(int id) {
            Order.this.id = id;
            return this;
        }

        public Builder setUserId(int id) {
            Order.this.userId = id;
            return this;
        }

        public Builder setState(boolean state) {
            Order.this.closed = state;
            return this;
        }

        public Builder setAmount(double amount) {
            Order.this.amount = amount;
            return this;
        }
        public Builder setCash(boolean cash){
            Order.this.cash = cash;
            return this;
        }
        public Builder setOrderDateTime(Timestamp timestamp) {
            Order.this.orderDateTime = timestamp;
            return this;
        }
        public Builder setClosedDateTime(Timestamp timestamp){
            Order.this.closedDateTime = timestamp;
            return this;
        }

        public Order build() {
            return Order.this;
        }
    }
}
