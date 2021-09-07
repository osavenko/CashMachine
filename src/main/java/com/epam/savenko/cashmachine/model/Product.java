package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {

    private static final long serialVersionUID = 8100091839100723221L;

    private int id;
    private String name;
    private int brandId;
    private double price;
    private int quantity;
    private boolean weight;

    public Product() {
    }

    public Product(String name, int brandId, double price, int quantity, boolean weight) {
        this.name = name;
        this.brandId = brandId;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
    }

    public Product(String name, Brand brand, double price, int quantity, boolean weight) {
        this(name, brand.getId(), price, quantity, weight);
    }

    public Product(int id, String name, int brandId, double price, int quantity, boolean weight) {
        this.id = id;
        this.name = name;
        this.brandId = brandId;
        this.price = price;
        this.quantity = quantity;
        this.weight = weight;
    }

    public Product(int id, String name, Brand brand, double price, int quantity, boolean weight) {
        this(id, name, brand.getId(), price, quantity, weight);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setBrandId(Brand brand) {
        this.brandId = brand.getId();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isWeight() {
        return weight;
    }

    public void setWeight(boolean weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return id == product.id && brandId == product.brandId && Double.compare(product.price, price) == 0 && quantity == product.quantity && weight == product.weight && name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brandId, price, quantity, weight);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brandId=" + brandId +
                ", price=" + price +
                ", quantity=" + quantity +
                ", weight=" + weight +
                '}';
    }
}
