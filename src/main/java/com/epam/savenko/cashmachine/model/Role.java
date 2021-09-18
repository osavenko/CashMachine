package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * The class describe roles for application users
 *
 * @author Oleg Savenko
 * @version 1.0
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 1375413957481220091L;

    /**
     * field id
     */
    private int id;
    /**
     * field name
     */
    private String name;

    /**
     * Initializes a newly created Role object.
     *
     * @see Role#Role(String)
     * @see Role#Role(int, String)
     */
    public Role() {
    }

    /**
     * Initializes a newly created Role object with <b>name</b>.
     *
     * @param name - name of role
     * @see Role#Role()
     * @see Role#Role(int, String)
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Initializes a newly created Role object with two parameters <b>id</b>, <b>name</b>.
     *
     * @param id   - id role
     * @param name - name of role
     * @see Role#Role()
     * @see Role#Role(String)
     */
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets id role. This is field {@link Role#id}.
     *
     * @return id role
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id role. This is field {@link Role#id}.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name of role. This is field {@link Role#name}.
     *
     * @return name of role
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of role. This is field {@link Role#name}.
     *
     * @param name - name of role
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
        Role role = (Role) o;
        return id == role.id && name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "id: " + id + " name: " + name;
    }
}
