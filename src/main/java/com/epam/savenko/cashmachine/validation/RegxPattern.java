package com.epam.savenko.cashmachine.validation;

import java.util.regex.Pattern;

public class RegxPattern {
    private RegxPattern() {
    }

    public static final String PRODUCT_NAME = "^[0-9a-zA-Zа-яА-ЯЄєіІїЇёЁ\\-\\s]{4,100}$";
    public static final String USER_NAME = "^[0-9a-zA-Zа-яА-ЯЄєіІїЇёЁ]{4,20}$";
}
