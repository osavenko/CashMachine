package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.OrderDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FixZReportCommand extends Command {
    private static final Logger LOG = Logger.getLogger(FixZReportCommand.class);
    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_ERROR, RouteType.REDIRECT);
        OrderDao orderDao = new JdbcOrderDaoImpl();
        try {
            orderDao.deleteAll();
            forward.setPath("/controller?command=zreport");
            forward.setRouteType(RouteType.FORWARD);
        } catch (CashMachineException e) {
            LOG.error("Error when delete: ",e);
        }
        return forward;
    }
}
