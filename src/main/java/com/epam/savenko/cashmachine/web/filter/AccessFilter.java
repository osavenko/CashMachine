package com.epam.savenko.cashmachine.web.filter;

import com.epam.savenko.cashmachine.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AccessFilter implements Filter {


    private static final Logger LOG = Logger.getLogger(AccessFilter.class);
    private static List<String> outOfControl = new ArrayList<String>();
    private static List<String> commons = new ArrayList<String>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.debug("Started AccessFilter");
        if (haveAccess(servletRequest)) {
            LOG.debug("Finished AccessFilter");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String errorMessage = "You do not have permission to access the requested resource";
            servletRequest.setAttribute("errorMessage", errorMessage);
            LOG.debug("Access filter message: " + errorMessage);
            servletRequest.getRequestDispatcher(Path.PAGE_BAD_LOGIN)
                    .forward(servletRequest, servletResponse);
        }
    }

    private boolean haveAccess(ServletRequest req) {
        HttpServletRequest httpRequest = (HttpServletRequest) req;

        String commandName = httpRequest.getParameter("command");
        if (commandName == null || commandName.isEmpty()) {
            LOG.debug("AccessFilter command is empty ");
            return false;
        }
        LOG.debug("AccessFilter command = " + commandName);

        if (outOfControl.contains(commandName)) {
            return true;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null) {
            return false;
        }
        return commons.contains(commandName);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug("Started initialisation AccessFilter");
        // out of control
        outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
        LOG.debug("outOfControl initialised " + outOfControl.size() + " commands");

        commons = asList(filterConfig.getInitParameter("common"));
        LOG.debug("common initialised " + commons.size() + " commands");

        LOG.debug("Finished initialisation AccessFilter");
    }

    private List<String> asList(String initParameter) {
        List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(initParameter);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }
}
