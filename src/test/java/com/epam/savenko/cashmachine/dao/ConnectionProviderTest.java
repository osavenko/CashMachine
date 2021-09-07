package com.epam.savenko.cashmachine.dao;

import com.epam.savenko.cashmachine.exception.CashMachineException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionProviderTest extends Assertions {

    @Test
    void getInstance() {
        assertThrows(CashMachineException.class,()->{
            ConnectionProvider.getInstance();
        });
    }
}