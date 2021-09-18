package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class describes locales in application.
 *
 * @author Oleh Savenko
 * @version 1.0
 */
public class Locale implements Serializable {

    private static final long serialVersionUID = -8052717055666149796L;
    /**
     * field id
     */
    private int id;
    /**
     * field name
     */
    private String name;
    /**
     * filed description
     */
    private String description;

    /**
     * Initializes a newly created Locale object.
     * @see Locale#Locale(String, String)
     * @see Locale#Locale(int, String, String)
     */
    public Locale() {
    }

    /**
     * Initializes a newly created Locale object. Sets two parameters <b>name</b>, <b>description</b>
     * @param name - locale name
     * @param description - locale description
     * @see Locale#Locale()
     * @see Locale#Locale(int, String, String)
     */
    public Locale(String name, String description) {
        this.name = name;
        this.description = description;
    }
    /**
     * Initializes a newly created Locale object. Sets three parameters <b>id</b>, <b>name</b>, <b>description</b>
     * @param id - locale id
     * @param name - locale name
     * @param description - locale description
     * @see Locale#Locale()
     * @see Locale#Locale(String, String)
     */

    public Locale(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets value field {@link Locale#id}
     * @return id locale (value {@link Locale#id})
     */
    public int getId() {
        return id;
    }

    /**
     * Sets value field {@link Locale#id}
     * @param id - locale id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets short name locale like <b>ua</b>, <b>ru</b>... This function gets value field {@link Locale#name}
     * @return short name locale
     */
    public String getName() {
        return name;
    }

    /**
     * Sets short name locale like <b>ua</b>, <b>ru</b>...
     * @param name - short name locale
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description locale
     * @return description locale
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description locale
     * @param description - description locale
     */
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
