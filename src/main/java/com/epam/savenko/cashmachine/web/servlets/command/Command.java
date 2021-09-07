package com.epam.savenko.cashmachine.web.servlets.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {

    private static final long serialVersionUID = 485803925006503340L;

    /**
     * Execution method for command.
     * @return Address to go once the command is executed.
     */
    public abstract String execute(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }
}
