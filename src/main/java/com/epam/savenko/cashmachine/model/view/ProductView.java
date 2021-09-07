package com.epam.savenko.cashmachine.model.view;

import com.epam.savenko.cashmachine.model.LocaleProduct;
import com.epam.savenko.cashmachine.model.Product;

import java.io.Serializable;
import java.util.List;

public class ProductView implements Serializable {

    private static final long serialVersionUID = -3733252040592313428L;

    private Product product;
    private List<LocaleProduct> localeProducts;

    public ProductView() {
    }

    public ProductView(Product product) {
        this.product = product;
    }

    public ProductView(Product product, List<LocaleProduct> localeProducts) {
        this.product = product;
        this.localeProducts = localeProducts;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<LocaleProduct> getLocaleProducts() {
        return localeProducts;
    }

    public void setLocaleProducts(List<LocaleProduct> localeProducts) {
        this.localeProducts = localeProducts;
    }
}
