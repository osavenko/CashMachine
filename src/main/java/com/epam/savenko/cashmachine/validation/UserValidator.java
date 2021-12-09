package com.epam.savenko.cashmachine.validation;

import com.epam.savenko.cashmachine.model.User;

import java.util.regex.Pattern;

public class UserValidator implements Validator {
    private User user;

    public UserValidator(User user) {
        this.user = user;
    }

    @Override
    public boolean validate() {
        if (user == null) {
            return false;
        }
        if (!Pattern.matches(RegxPattern.USER_NAME, user.getName())) {
            return false;
        }
        return true;
    }
}
