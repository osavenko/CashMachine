package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.web.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand extends Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        return Path.PAGE_NO_COMMAND;
    }
}
