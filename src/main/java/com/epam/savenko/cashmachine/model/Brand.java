package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class describe brands of products.The class Brand has two properties <b>id</b> and <b>name</b>.
 *
 * @author Oleh Savenko
 * @version 1.0
 */
public class Brand implements Serializable {

    private static final long serialVersionUID = 5068612673742015128L;

    /**
     * field id
     */
    private int id;
    /**
     * field name
     */
    private String name;

    /**
     * Initializes a newly created Brand object.
     *
     * @see Brand#Brand(String)
     * @see Brand#Brand(int, String)
     */
    public Brand() {
    }

    /**
     * Initializes a newly created Brand object. Constructor sets one parameter <b>name</b> brand.
     *
     * @param name - name brand.
     * @see Brand#Brand(int, String)
     * @see Brand#Brand()
     */
    public Brand(String name) {
        this.name = name;
    }

    /**
     * Initializes a newly created Brand object. Constructor sets two parameters <b>id</b>, <b>name</b>.
     *
     * @param id   - id brand
     * @param name - name brand
     * @see Brand#Brand()
     * @see Brand#Brand(String)
     */
    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets value field {@link Brand#id}
     *
     * @return id brand (value field {@link Brand#id})
     */
    public int getId() {
        return id;
    }

    /**
     * Sets value field {@link Brand#id}
     *
     * @param id - id brand
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets value field {@link Brand#name}
     *
     * @return name brand (value field {@link Brand#name})
     */
    public String getName() {
        return name;
    }

    /**
     * Sets value field {@link Brand#name}
     *
     * @param name - name brand
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Brand brand = (Brand) o;
        return id == brand.id && name.equals(brand.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
