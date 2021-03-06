package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.exception.CashMachineException;
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

public class ZReportCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ZReportCommand.class);
    private static final long serialVersionUID = 7170595419087144360L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_TAX_REPORT, RouteType.FORWARD);
        HttpSession session = req.getSession();
        try {
            XReportCommand.setData(session, LOG);
        } catch (CashMachineException e) {
            LOG.error("Error when receive parameters: ", e);
        }

        session.setAttribute(SessionParam.REPORT_TYPE, "z");
        return forward;
    }
}
