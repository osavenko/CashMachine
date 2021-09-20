package com.epam.savenko.cashmachine.model;

import com.epam.savenko.cashmachine.model.view.ProductDescriptionView;

import java.io.Serializable;
import java.util.List;
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

    public static Builder newBuilder() {
        return new Product().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(int id) {
            Product.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            Product.this.name = name;
            return this;
        }

        public Builder setBrandId(int brandId) {
            Product.this.brandId = brandId;
            return this;
        }

        public Builder setPrice(double price) {
            Product.this.price = price;
            return this;
        }

        public Builder setQuantity(int quantity) {
            Product.this.quantity = quantity;
            return this;
        }

        public Builder setWeight(boolean weight) {
            Product.this.weight = weight;
            return this;
        }

        public Product build() {
            return Product.this;
        }
    }
}
