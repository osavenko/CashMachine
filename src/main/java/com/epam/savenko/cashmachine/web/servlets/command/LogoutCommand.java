package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand extends Command {

    private static final Logger LOG = Logger.getLogger(LogoutCommand.class);

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        LOG.debug("Command logout starts");

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LOG.debug("Command logout finished");

        return new RoutePath(Path.PAGE_LOGIN, RouteType.FORWARD);
    }
}
