package com.epam.savenko.cashmachine.model.view;

import com.epam.savenko.cashmachine.model.Product;

import java.io.Serializable;
import java.util.List;

public class ProductView implements Serializable {

    private static final long serialVersionUID = -3733252040592313428L;

    private Product product;
    private List<ProductDescriptionView> descriptionViews;
    private String localeDescription;

    public ProductView() {
    }

    public ProductView(Product product, List<ProductDescriptionView> localeProducts) {
        this.product = product;
        this.descriptionViews = localeProducts;
    }

    public ProductView(Product product, String localeDescription) {
        this.product = product;
        this.localeDescription = localeDescription;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ProductDescriptionView> getDescriptionViews() {
        return descriptionViews;
    }

    public void setDescriptionViews(List<ProductDescriptionView> descriptionViews) {
        this.descriptionViews = descriptionViews;
    }

    public String getLocaleDescription() {
        return localeDescription;
    }

    public void setLocaleDescription(String localeDescription) {
        this.localeDescription = localeDescription;
    }
}
