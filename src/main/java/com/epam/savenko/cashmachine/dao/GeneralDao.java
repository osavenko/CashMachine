package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;

import java.util.List;
import java.util.Optional;

public interface GeneralDao<E, Key> {
    Optional<E> findById(Key key) throws CashMachineException;
    List<E> findAll() throws CashMachineException;

    Optional<E> insert(E entity) throws CashMachineException;
    boolean update(E entity) throws CashMachineException;
    boolean delete(int id) throws CashMachineException;
}
