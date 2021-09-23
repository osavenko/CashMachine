package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Locale;

import java.util.Optional;

public interface LocaleDao extends GeneralDao<Locale, Integer> {
    Optional<Locale> findByName(String name) throws CashMachineException;
}
