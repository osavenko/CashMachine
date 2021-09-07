package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.UserDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcUserDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand extends Command {

    private static final long serialVersionUID = 5430749800774281878L;

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        LOG.debug("Start check user");
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        LOG.debug("Check user with name " + login);
        String password = req.getParameter("password");

        String errorMessage = null;
        String forward = Path.PAGE_BAD_LOGIN;
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            LOG.debug("Null user name or password(login=" + login + ")");
            errorMessage = "Login/password cannot be empty";
            req.setAttribute("errorMessage", errorMessage);
            LOG.error("errorMessage --> " + errorMessage);
            return forward;
        }
        LOG.debug("User name and password is not null");
        UserDao userDao = new JdbcUserDaoImpl();
        Optional<User> oUser = null;

        try {
            LOG.debug("Login: "+login+", password: "+password);
            oUser = userDao.check(login, User.getHash().apply(password));
            if (!oUser.isPresent()) {
                errorMessage = "Bad login or password";
                LOG.debug(errorMessage);
                req.setAttribute("errorMessage", errorMessage);
                LOG.error("errorMessage --> " + errorMessage + ", login=" + login);
                return forward;
            } else {
                LOG.debug("User valid(user=" + oUser.get());
                forward = Path.PAGE_MAIN;
            }
        } catch (CashMachineException e) {
            e.printStackTrace();
        }
        return forward;
    }
}
