package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.User;

import java.util.Optional;

public interface UserDao extends GeneralDao<User, Integer> {

    boolean setPassword(User user, String hash) throws CashMachineException;

    Optional<User> check(String login, String hash) throws CashMachineException;
}
