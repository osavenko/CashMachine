package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class describe user details.
 *
 * @author Oleh Savenko
 * @version 1.0
 * @see User
 */
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 5838075762763878848L;

    /**
     * field id
     */
    private int id;
    /**
     * field fullName
     */
    private String fullName;
    /**
     * field userId
     */
    private int userId;

    /**
     * Initializes a newly created UserDetails object.
     *
     * @see UserDetails#UserDetails(String, int)
     * @see UserDetails#UserDetails(String, User)
     * @see UserDetails#UserDetails(int, String, int)
     * @see UserDetails#UserDetails(int, String, User)
     */
    public UserDetails() {
    }

    /**
     * Initializes a newly created UserDetails object.
     * Object initializes with two parameter <b>fullName</b>, <b>userId</b>
     *
     * @param fullName - full name of user
     * @param userId   - id of user
     * @see UserDetails#UserDetails()
     * @see UserDetails#UserDetails(String, User)
     * @see UserDetails#UserDetails(int, String, int)
     * @see UserDetails#UserDetails(int, String, User)
     */
    public UserDetails(String fullName, int userId) {
        this.fullName = fullName;
        this.userId = userId;
    }

    /**
     * Initializes a newly created UserDetails object.
     * Object initializes with two parameter <b>fullName</b>, <b>user</b>
     *
     * @param fullName - full name of user
     * @param user     - object of User`s class
     * @see UserDetails#UserDetails()
     * @see UserDetails#UserDetails(String, int)
     * @see UserDetails#UserDetails(int, String, int)
     * @see UserDetails#UserDetails(int, String, User)
     */
    public UserDetails(String fullName, User user) {
        this.fullName = fullName;
        this.userId = user.getId();
    }

    /**
     * Initializes a newly created UserDetails object.
     * Object initializes with three parameter <b>id</b>, <b>fullName</b>, <b>userId</b>
     *
     * @param id       - id user details
     * @param fullName - full name of user
     * @param userId   - id of user
     * @see UserDetails#UserDetails()
     * @see UserDetails#UserDetails(String, int)
     * @see UserDetails#UserDetails(String, User)
     * @see UserDetails#UserDetails(int, String, User)
     */
    public UserDetails(int id, String fullName, int userId) {
        this.id = id;
        this.fullName = fullName;
        this.userId = userId;
    }

    /**
     * Initializes a newly created UserDetails object.
     * Object initializes with three parameter <b>id</b>, <b>fullName</b>, <b>user</b>
     *
     * @param id       - id user details
     * @param fullName - full name of user
     * @param user     - object of User`s class
     * @see UserDetails#UserDetails()
     * @see UserDetails#UserDetails(String, int)
     * @see UserDetails#UserDetails(String, User)
     * @see UserDetails#UserDetails(int, String, int)
     */
    public UserDetails(int id, String fullName, User user) {
        this(id, fullName, user.getId());
    }

    /**
     * Gets id of user details. This is field {@link UserDetails#id}.
     *
     * @return id of user details
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id of user details. This is field {@link UserDetails#id}.
     *
     * @param id - id of user details
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets user`s full name. This is field {@link UserDetails#fullName}.
     *
     * @return full name of user
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets full name of user. This is field {@link UserDetails#fullName}.
     *
     * @param fullName - full name of user
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets id of user. This is field {@link UserDetails#userId}.
     *
     * @return id of user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets id of user. This is field {@link UserDetails#userId}
     *
     * @param userId id user
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Sets id of user. This is field {@link UserDetails#userId}
     *
     * @param user the object of User`s class
     */
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
