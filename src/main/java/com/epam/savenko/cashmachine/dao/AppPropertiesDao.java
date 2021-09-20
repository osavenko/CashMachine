package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.AppProperties;

import java.util.Optional;

public interface AppPropertiesDao{
    Optional<AppProperties> getByName(String name) throws CashMachineException;
}
