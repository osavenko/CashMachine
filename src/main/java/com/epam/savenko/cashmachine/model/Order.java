package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Serializable {

    private static final long serialVersionUID = -1948123483814564253L;

    private int id;
    private int userId;
    private boolean closed;
    private double amount;

    public Order() {
    }

    public Order(int userId, boolean closed, double amount) {
        this.userId = userId;
        this.closed = closed;
        this.amount = amount;
    }

    public Order(int id, int userId, boolean closed, double amount) {
        this.id = id;
        this.userId = userId;
        this.closed = closed;
        this.amount = amount;
    }

    public Order(User user, boolean closed, double amount) {
        this(user.getId(), closed, amount);
    }

    public Order(int id, User user, boolean closed, double amount) {
        this(id, user.getId(), closed, amount);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Order order = (Order) o;
        return id == order.id && userId == order.userId && closed == order.closed && Double.compare(order.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, closed, amount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", closed=" + closed +
                ", amount=" + amount +
                '}';
    }
}
