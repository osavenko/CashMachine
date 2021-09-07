package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

public class UserDetails implements Serializable {

    private static final long serialVersionUID = 5838075762763878848L;

    private int id;
    private String fullName;
    private int userId;

    public UserDetails() {
    }

    public UserDetails(String fullName, int userId) {
        this.fullName = fullName;
        this.userId = userId;
    }

    public UserDetails(String fullName, User user) {
        this.fullName = fullName;
        this.userId = user.getId();
    }

    public UserDetails(int id, String fullName, int userId) {
        this.id = id;
        this.fullName = fullName;
        this.userId = userId;
    }

    public UserDetails(int id, String fullName, User user) {
        this(id, fullName, user.getId());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserId(User user) {
        this.userId = user.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetails that = (UserDetails) o;
        return id == that.id && userId == that.userId && fullName.equals(that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, userId);
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
