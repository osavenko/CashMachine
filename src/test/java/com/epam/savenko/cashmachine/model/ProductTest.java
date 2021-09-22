package com.epam.savenko.cashmachine.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void newBuilder() {
        Product actual = Product.newBuilder()
                .setId(1)
                .setName("")
                .setBrandId(1)
                .setPrice(1.2)
                .setWeight(true)
                .setQuantity(1)
                .build();
        Product expexted = new Product();
        expexted.setId(1);
        expexted.setName("");
        expexted.setBrandId(1);
        expexted.setPrice(1.2);
        expexted.setWeight(true);
        expexted.setQuantity(1);

        assertEquals(expexted,actual);
    }
}