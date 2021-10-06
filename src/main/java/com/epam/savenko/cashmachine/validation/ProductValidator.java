package com.epam.savenko.cashmachine.validation;

import com.epam.savenko.cashmachine.model.Product;

import java.util.regex.Pattern;

public class ProductValidator implements Validator {
    private Product product;

    public ProductValidator(Product product) {
        this.product = product;
    }

    @Override
    public boolean validate() {
        if (product == null) {
            return false;
        }
        if (product.getPrice() < 0 || product.getQuantity() < 0) {
            return false;
        }
        if (!Pattern.matches(RegxPattern.PRODUCT_NAME, product.getName())) {
            return false;
        }
        return true;
    }
}
