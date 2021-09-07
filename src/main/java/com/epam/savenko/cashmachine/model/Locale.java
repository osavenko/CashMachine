package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

public class Locale implements Serializable {

    private static final long serialVersionUID = -8052717055666149796L;

    private int id;
    private String name;
    private String description;

    public Locale() {
    }

    public Locale(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Locale(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Locale locale = (Locale) o;
        return id == locale.id && name.equals(locale.name) && Objects.equals(description, locale.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return name;
    }
}
