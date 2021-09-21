package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.AppPropertiesDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcAppPropertiesDao;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.AppProperties;
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

public class XReportCommand extends Command {

    private static final Logger LOG = Logger.getLogger(XReportCommand.class);

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_TAX_REPORT, RouteType.FORWARD);
        HttpSession session = req.getSession();
        try {
            AppPropertiesDao appProperties = new JdbcAppPropertiesDao();
            session.setAttribute(SessionParam.STORE_NAME, appProperties.getByName("name").get().getValue());
            session.setAttribute(SessionParam.ADDRESS, appProperties.getByName("address").get().getValue());
            session.setAttribute(SessionParam.IPN, appProperties.getByName("taxnumber").get().getValue());


        } catch (CashMachineException e) {
            LOG.error("Error when receive parameters: ", e);
        }
        session.setAttribute(SessionParam.REPORT_TYPE, "x");
        return forward;
    }
}
