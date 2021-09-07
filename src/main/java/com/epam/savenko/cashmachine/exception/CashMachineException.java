package com.epam.savenko.cashmachine.exception;

public class CashMachineException extends Exception{
    public CashMachineException(String message) {
        super(message);
    }

    public CashMachineException(String message, Throwable cause) {
        super(message, cause);
    }
}
