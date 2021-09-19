package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainPageCommand extends Command{

    private static final Logger LOG = Logger.getLogger(MainPageCommand.class);
    private static final long serialVersionUID = -6464059499920319851L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        LOG.debug("Start MainPageCommand");
        RoutePath forward = new RoutePath(Path.PAGE_MAIN, RouteType.FORWARD);

        LOG.debug("Start MainPageCommand");
        return forward;
    }
}
