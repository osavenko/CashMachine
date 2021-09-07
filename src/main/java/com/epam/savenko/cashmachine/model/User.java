package com.epam.savenko.cashmachine.model;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.function.Function;

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
        digest.update(pass.getBytes());
        byte[] hashData = digest.digest();
        StringBuilder rez = new StringBuilder();
        for (byte b : hashData) {
            String currentHex = Integer.toHexString(b & 0xFF);
            if (currentHex.length() == 1) {
                rez.append('0');
            }
            rez.append(currentHex);
        }
        return rez.toString();
    };

    public static Function<String, String> getHash(){
        return hash;
    }
    private int id;
    private String name;
    private int roleId;
    private int localeId;
    private boolean activated;

    public User() {
    }

    public User(String name, int roleId, int localeId, boolean activated) {
        this.name = name;
        this.roleId = roleId;
        this.localeId = localeId;
        this.activated = activated;
    }

    public User(int id, String name, int roleId, int localeId, boolean activated) {
        this.id = id;
        this.name = name;
        this.roleId = roleId;
        this.localeId = localeId;
        this.activated = activated;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getLocaleId() {
        return localeId;
    }

    public void setLocaleId(int localeId) {
        this.localeId = localeId;
    }

    public boolean isActivated() {
        return activated;
    }

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
