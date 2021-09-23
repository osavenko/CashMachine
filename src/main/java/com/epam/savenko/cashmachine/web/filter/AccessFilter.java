package com.epam.savenko.cashmachine.web.filter;

import com.epam.savenko.cashmachine.dao.MenuDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcMenuDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.web.constant.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class AccessFilter implements Filter {


    private static final Logger LOG = Logger.getLogger(AccessFilter.class);
    private static Map<Integer, List<String>> accessMap = new HashMap<>();
    private static List<String> outOfControl = new ArrayList<>();
    private static List<String> commons = new ArrayList<>();

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
        String changeLanguage = httpRequest.getParameter("changeLanguage");

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

        User user = (User) session.getAttribute("cashUser");
        if (user == null) {
            return false;
        }
        LOG.debug("User in system: " + user);
        LOG.debug("Commands list to user: " + accessMap.get(user.getRoleId()).contains(commandName));

        return accessMap.get(user.getRoleId()).contains(commandName) || commons.contains(commandName);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        LOG.debug("Started initialisation AccessFilter");
        // out of control
        outOfControl = asList(filterConfig.getInitParameter("out-of-control"));
        LOG.debug("outOfControl initialised " + outOfControl.size() + " commands");

        commons = asList(filterConfig.getInitParameter("common"));
        LOG.debug("common initialised " + commons.size() + " commands");

        MenuDao menuDao = new JdbcMenuDaoImpl();
        try {
            List<Integer> rolesId = menuDao.findAllRolesInAccessMenuItem();
            accessMap.put(0, menuDao.findCommandByRole(0));
            for (Integer roleId : rolesId) {
                accessMap.put(roleId, menuDao.findCommandByRole(roleId));
                LOG.debug("Initialised commands to roleId: " + roleId + ", commands list: " + accessMap.get(roleId));
            }
            LOG.debug("SELECTED ROLES_ID: " + rolesId);
        } catch (CashMachineException e) {
            LOG.error("Error MenuAccessItem" + e.getMessage());
        }

        LOG.debug("Finished initialisation AccessFilter");
    }

    private List<String> asList(String initParameter) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(initParameter);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }
}
