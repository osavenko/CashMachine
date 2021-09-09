package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectCommand extends Command {
    private static final Logger LOG = Logger.getLogger(RedirectCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        String forward = Path.PAGE_BAD_LOGIN;
        String errorMessage = "Error redirect";
        String command = req.getParameter("command");
        LOG.debug("Redirect command: " + command);
        if ("addproductpage".equals(command)) {
            LOG.debug("Redirect page: " + Path.PAGE_TO_ADD_PRODUCTS);
            return Path.PAGE_TO_ADD_PRODUCTS;
        }
        if ("main".equals(command)) {
            LOG.debug("Redirect page: " + Path.PAGE_MAIN);
            return Path.PAGE_MAIN;
        }
        return forward;
    }
}
