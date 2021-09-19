package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.OrderDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcUserDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Order;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.web.WebUtil;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrdersListCommand extends Command {

    private static final Logger LOG = Logger.getLogger(OrdersListCommand.class);
    public static final int ROWS_IN_PAGE = 5;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_ORDERS_LIST, RouteType.FORWARD);

        HttpSession session = req.getSession();
        session.setAttribute("offset", ROWS_IN_PAGE);
        Integer start = WebUtil.getNumberStartPage(req, session, LOG);
        try {
            List<Order> orders = new JdbcOrderDaoImpl().findAll();
            if (!orders.isEmpty()) {
                session.setAttribute(SessionParam.ORDER_COUNT, orders.size());
                session.setAttribute(SessionParam.ORDER_LIST, orders);
            }
            List<User> userList = new JdbcUserDaoImpl().findAll();
            Map<Integer, String> usersMap = userList.stream()
                    .collect(Collectors.toMap(User::getId, User::getName));
            if(!usersMap.isEmpty()){
                session.setAttribute(SessionParam.ALL_USERS, usersMap);
            }
        } catch (CashMachineException e) {
            LOG.error("JDBC Error", e);
        }
        return forward;
    }
}
