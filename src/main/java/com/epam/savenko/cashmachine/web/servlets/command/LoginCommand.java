package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.UserDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcUserDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Locale;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.constant.SessionParam;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.epam.savenko.cashmachine.web.constant.SessionParam.*;

public class LoginCommand extends Command {

    private static final long serialVersionUID = 5430749800774281878L;

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        LOG.debug("Start check user");
        HttpSession session = req.getSession();
        String login = req.getParameter(LOGIN);
        LOG.debug("Check user with name " + login);
        String password = req.getParameter(PASSWORD);

        String errorMessage = null;
        RoutePath forward = new RoutePath(Path.PAGE_BAD_LOGIN, RouteType.REDIRECT);
        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            req.setAttribute("errorMessage", errorMessage);
            LOG.error("errorMessage --> " + errorMessage);
            return forward;
        }
        LOG.debug("User name and password is not null");
        UserDao userDao = new JdbcUserDaoImpl();
        Optional<User> oUser = null;

        try {
            LOG.debug("Login: " + login + ", password: " + password);
            oUser = userDao.check(login, User.getHash().apply(password));
            if (!oUser.isPresent()) {
                errorMessage = "Bad login or password";
                LOG.debug(errorMessage);
                req.setAttribute("errorMessage", errorMessage);
                LOG.error("errorMessage --> " + errorMessage + ", login=" + login);
                return forward;
            } else {
                session.setAttribute(USER, oUser.get());
                Optional<Locale> locale = new JdbcLocaleDaoImpl().findById(oUser.get().getLocaleId());
                session.setAttribute(SessionParam.LANGUAGE, locale.isPresent() ? locale.get().getName() : "en");
                LOG.debug("User valid(user=" + oUser.get());
                forward.setPath("/controller?command=main");
                forward.setRouteType(RouteType.REDIRECT);
            }
        } catch (CashMachineException e) {
            LOG.error("Error when login");
        }
        return forward;
    }
}
