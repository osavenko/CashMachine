package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;

public interface Count {
    int getCount() throws CashMachineException;
}
