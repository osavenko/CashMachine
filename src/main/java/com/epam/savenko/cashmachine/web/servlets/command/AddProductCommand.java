package com.epam.savenko.cashmachine.web.servlets.command;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductCommand extends Command{

    private static final Logger LOG = Logger.getLogger(AddProductCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        return null;
    }
}
