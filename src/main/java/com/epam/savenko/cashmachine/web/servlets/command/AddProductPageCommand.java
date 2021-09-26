package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcAppPropertiesDao;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.AppProperties;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static com.epam.savenko.cashmachine.web.constant.RequestParam.COMMAND;
import static com.epam.savenko.cashmachine.web.constant.SessionParam.DEFAULT_LOCALE;

public class AddProductPageCommand extends Command {

    private static final Logger LOG = Logger.getLogger(AddProductPageCommand.class);
    private static final long serialVersionUID = -7708447895901535142L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_BAD_LOGIN, RouteType.FORWARD);
        String command = req.getParameter(COMMAND);
        LOG.debug("Redirect command: " + command);
        forward.setPath(Path.PAGE_TO_ADD_PRODUCTS);
        HttpSession session = req.getSession();
        try {
            String defaultLocale = "";
            Optional<AppProperties> appLocale = new JdbcAppPropertiesDao().getByName("locale");
            if (appLocale.isPresent()){
                defaultLocale = appLocale.get().getValue();
            }
            session.setAttribute(DEFAULT_LOCALE, defaultLocale);
        } catch (CashMachineException e) {
            LOG.error("Error when receive default locale");
            session.setAttribute(DEFAULT_LOCALE, "");
        }
        LOG.debug("Redirect page: " + Path.PAGE_TO_ADD_PRODUCTS);
        return forward;
    }
}
