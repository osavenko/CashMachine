package com.epam.savenko.cashmachine.model;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.function.Function;

/**
 * The class describe user of application.
 *
 * @author Oleh Savenko
 * @version 1.0
 * @see Role
 * @see Locale
 */
public class User implements Serializable {

    private static final Logger LOG = Logger.getLogger(User.class.getName());
    private static final long serialVersionUID = 856156479878950887L;

    private static final Function<String, String> hash = (pass) -> {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LOG.error("", e);
        }
        StringBuilder rez = new StringBuilder();
        if (digest == null) {
            return rez.toString();
        }
        digest.update(pass.getBytes());
        byte[] hashData = digest.digest();
        for (byte b : hashData) {
            String currentHex = Integer.toHexString(b & 0xFF);
            if (currentHex.length() == 1) {
                rez.append('0');
            }
            rez.append(currentHex);
        }
        return rez.toString();
    };

    /**
     * Static function, gets implementation functional interface to hash user`s password.
     * To hashing was used MassageDigest.
     *
     * @return implementation functional interface {@link Function}
     * @see Function
     * @see MessageDigest
     */
    public static Function<String, String> getHash() {
        return hash;
    }

    /**
     * field id
     */
    private int id;
    /**
     * field name
     */
    private String name;
    /**
     * field roleId
     */
    private int roleId;
    /**
     * field localeId
     */
    private int localeId;
    /**
     * field activated
     */
    private boolean activated;

    /**
     * Initializes a newly created User object.
     *
     * @see User#User(String, int, int, boolean)
     * @see User#User(int, String, int, int, boolean)
     */
    public User() {
    }

    /**
     * Initializes a newly created User object.
     * Object initializes with four parameters <b>name</b>, <b>roleId</b>, <b>?localeId</b>, <b>activated</b>
     *
     * @param name      - name of user
     * @param roleId    - id for the role to a user
     * @param localeId  - id for the locale to a user
     * @param activated - user activation label in application
     * @see User#User()
     * @see User#User(int, String, int, int, boolean)
     */
    public User(String name, int roleId, int localeId, boolean activated) {
        this.name = name;
        this.roleId = roleId;
        this.localeId = localeId;
        this.activated = activated;
    }

    /**
     * Initializes a newly created User object.
     * Object initializes with five parameters <b>id</b>, <b>name</b>, <b>roleId</b>, <b>?localeId</b>, <b>activated</b>
     *
     * @param id        - user id
     * @param name      - name of user
     * @param roleId    - id for the role to a user
     * @param localeId  - id for the locale to a user
     * @param activated - user activation label in application
     * @see User#User()
     * @see User#User(String, int, int, boolean)
     */
    public User(int id, String name, int roleId, int localeId, boolean activated) {
        this.id = id;
        this.name = name;
        this.roleId = roleId;
        this.localeId = localeId;
        this.activated = activated;
    }

    /**
     * Gets id user. This is field {@link User#id}.
     *
     * @return id user
     */
    public int getId() {
        return id;
    }

    /**
     * Sets user id. This is field {@link User#id}.
     *
     * @param id - user id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name of user. This is field {@link User#name}.
     *
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for the current user. This is field {@link User#name}.
     *
     * @param name - name of user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets id of role for the current user. This is field {@link User#roleId}.
     *
     * @return id of role for the current user
     * @see Role
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Sets id of role for the current user. This is field {@link User#roleId}.
     *
     * @param roleId id of role
     * @see Role
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Gets id of locale for the current user. This is field {@link User#localeId}.
     *
     * @return id of locale for the current user
     * @see Locale
     */
    public int getLocaleId() {
        return localeId;
    }

    /**
     * Sets id of locale for the current user. This is field {@link User#localeId}
     *
     * @param localeId id of locale
     * @see Locale
     */
    public void setLocaleId(int localeId) {
        this.localeId = localeId;
    }

    /**
     * Gets user activation label in application.
     *
     * @return activation label for the current user
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Sets user activation label in application.
     *
     * @param activated - activation label for the current user
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && roleId == user.roleId
                && localeId == user.localeId
                && activated == user.activated
                && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, roleId, localeId, activated);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roleId=" + roleId +
                ", localeId=" + localeId +
                ", activated=" + activated +
                '}';
    }
}
