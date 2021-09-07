package com.epam.savenko.cashmachine.model.view;

import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.model.UserDetails;

import java.io.Serializable;

public class UserView implements Serializable {

    private static final long serialVersionUID = 4937041120679808858L;

    private User user;
    private UserDetails details;

    public UserView() {
    }

    public UserView(User user) {
        this.user = user;
    }

    public UserView(User user, UserDetails details) {
        this.user = user;
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetails getDetails() {
        return details;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }
}
