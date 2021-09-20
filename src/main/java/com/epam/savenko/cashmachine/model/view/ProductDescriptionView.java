package com.epam.savenko.cashmachine.model.view;

public class ProductDescriptionView {
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
