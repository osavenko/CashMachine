package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

public class LocaleProduct implements Serializable {

    private static final long serialVersionUID = -3691850683333992842L;

    private  int id;
    private int locateId;
    private int productId;
    private String description;

    public LocaleProduct() {
    }

    public LocaleProduct(int locateId, int productId, String description) {
        this.locateId = locateId;
        this.productId = productId;
        this.description = description;
    }

    public LocaleProduct(Locale locale, Product product, String description) {
        this(locale.getId(), product.getId(), description);
    }

    public LocaleProduct(int id, int locateId, int productId, String description) {
        this.id = id;
        this.locateId = locateId;
        this.productId = productId;
        this.description = description;
    }
    public LocaleProduct(int id, Locale locale, Product product, String description) {
        this(id, locale.getId(), product.getId(), description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocateId() {
        return locateId;
    }

    public void setLocateId(int locateId) {
        this.locateId = locateId;
    }

    public void setLocateId(Locale locale) {
        this.locateId = locale.getId();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductId(Product product) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocaleProduct that = (LocaleProduct) o;
        return id == that.id && locateId == that.locateId && productId == that.productId && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locateId, productId, description);
    }

    @Override
    public String toString() {
        return "LocateProduct{" +
                "id=" + id +
                ", locateId=" + locateId +
                ", productId=" + productId +
                ", description='" + description + '\'' +
                '}';
    }
}
