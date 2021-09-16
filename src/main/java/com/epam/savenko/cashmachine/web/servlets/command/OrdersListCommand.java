package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.OrderDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.web.WebUtil;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OrdersListCommand extends Command {

    private static final Logger LOG = Logger.getLogger(OrdersListCommand.class);
    public static final int ROWS_IN_PAGE = 5;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_ORDERS_LIST, RouteType.FORWARD);
        OrderDao orderDao = new JdbcOrderDaoImpl();
        int ordersCount;

        HttpSession session = req.getSession();
        session.setAttribute("offset", ROWS_IN_PAGE);
        Integer start = WebUtil.getNumberStartPage(req, session, LOG);
        try {
            ordersCount = orderDao.getCount();
            session.setAttribute("ordersCount", ordersCount);
        } catch (CashMachineException e) {
            LOG.error("JDBC Error", e);
        }
        return forward;
    }
}
