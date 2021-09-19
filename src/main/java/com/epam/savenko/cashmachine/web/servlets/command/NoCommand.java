package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand extends Command{
    private static final long serialVersionUID = -5561463588390407190L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        return new RoutePath(Path.PAGE_NO_COMMAND, RouteType.REDIRECT);
    }
}
