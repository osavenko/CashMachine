package com.epam.savenko.cashmachine.model;

import java.io.Serializable;

public class AppProperties implements Serializable {

    private static final long serialVersionUID = -1948123653819864753L;

    private int id;
    private String name;
    private String value;

    public AppProperties() {
    }

    public AppProperties(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public AppProperties(int id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
