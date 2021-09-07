package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.UserDao;
import com.epam.savenko.cashmachine.dao.UserDetailsDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcRoleDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcUserDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcUserDetailsDaoImp;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Locale;
import com.epam.savenko.cashmachine.model.Role;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.model.UserDetails;
import com.epam.savenko.cashmachine.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class RegisterCommand extends Command {

    private static final Logger LOG = Logger.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        LOG.debug("Start registration");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullname");
        String sRole = req.getParameter("role");
        String sLocale = req.getParameter("locale");
        String forward = Path.PAGE_LOGIN;
        try {
            if (isValid(login, password, fullName, sRole, sLocale)) {
                int roleId = getRoleId(sRole);
                Optional<Role> oRole = new JdbcRoleDaoImpl().findById(roleId);
                if (!oRole.isPresent()) {
                    throw new CashMachineException("Role is empty");
                }
                LOG.debug("Got the role -->" + oRole.get());
                Optional<Locale> oLocale = new JdbcLocaleDaoImpl().findByName(sLocale);
                if (!oLocale.isPresent()) {
                    throw new CashMachineException("Locale is empty");
                }
                LOG.debug("Got the locale -->"+oLocale.get());
                User user = new User(login, oRole.get().getId(), oLocale.get().getId(), false);

                UserDao userDao = new JdbcUserDaoImpl();
                Optional<User> newUser = userDao.insert(user);
                if (!newUser.isPresent()) {
                    throw new CashMachineException("error while creating user");
                }
                LOG.debug("Created user -->"+newUser.get());

                UserDetailsDao userDetailsDao = new JdbcUserDetailsDaoImp();
                UserDetails userDetails = new UserDetails(fullName, newUser.get());
                Optional<UserDetails> newUserDetails = userDetailsDao.insert(userDetails);
                if (!newUser.isPresent()) {
                    throw new CashMachineException("error while creating user details");
                }
                LOG.debug("Saved user details -->"+newUserDetails.get());

                if (!userDao.setPassword(newUser.get(), User.getHash().apply(password))) {
                    throw new CashMachineException("error while setting user password");
                }
                LOG.debug("Installed user password ");
            }
        } catch (CashMachineException e) {
            LOG.debug("Registration error", e);
        }
        return forward;
    }

    private int getRoleId(String sRole) throws CashMachineException {
        int roleId;
        try {
            roleId = Integer.parseInt(sRole);
        } catch (NumberFormatException e) {
            LOG.debug("Error role id=" + sRole, e);
            throw new CashMachineException("Error role id=" + sRole, e);
        }
        return roleId;
    }

    private boolean isValid(String login, String password, String fullName, String sRole, String sLocale) {
        if ((login == null) || (login.isEmpty())) {
            LOG.debug("Login null or empty");
            return false;
        }
        if ((password == null) || (password.isEmpty())) {
            LOG.debug("Password null or empty");
            return false;
        }
        if ((fullName == null) || (password.isEmpty())) {
            LOG.debug("Full name null or empty");
            return false;
        }
        if ((sRole == null) || (sRole.isEmpty())) {
            LOG.debug("Role null or empty");
            return false;
        }
        if ((sLocale == null) || (sLocale.isEmpty())) {
            LOG.debug("Locale null or empty");
            return false;
        }
        return true;
    }
}
