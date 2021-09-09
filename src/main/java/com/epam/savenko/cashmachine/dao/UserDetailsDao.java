package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.UserDetails;

import java.util.Optional;

public interface UserDetailsDao extends GeneralDao<UserDetails, Integer> {
    Optional<UserDetails> findByUserId(Integer key) throws CashMachineException;
}
