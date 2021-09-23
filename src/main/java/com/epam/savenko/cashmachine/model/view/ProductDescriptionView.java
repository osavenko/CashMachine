package com.epam.savenko.cashmachine.model.view;

import java.io.Serializable;

public class ProductDescriptionView implements Serializable {

    private static final long serialVersionUID = -2940529075909113699L;

    private String locale;
    private String text;

    public ProductDescriptionView(String locale, String text) {
        this.locale = locale;
        this.text = text;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ProductDescriptionView{" +
                "locale='" + locale + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
