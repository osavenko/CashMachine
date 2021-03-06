package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.MenuDao;
import com.epam.savenko.cashmachine.dao.OrderDao;
import com.epam.savenko.cashmachine.dao.Permission;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcMenuDaoImpl;
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
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrdersListCommand extends Command {

    private static final Logger LOG = Logger.getLogger(OrdersListCommand.class);
    public static final int ROWS_IN_PAGE = 5;
    private static final long serialVersionUID = -6439949376258681075L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_ORDERS_LIST, RouteType.FORWARD);

        HttpSession session = req.getSession();
        session.setAttribute("offset", ROWS_IN_PAGE);
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            if ("curOrder".equals(parameterNames.nextElement())) {
                String currOrder = req.getParameter("curOrder");
                if (currOrder.length() > 0) {
                    try {
                        int orderId = Integer.parseInt(currOrder);
                        new JdbcOrderDaoImpl().delete(orderId);
                    } catch (NumberFormatException | CashMachineException e) {
                        LOG.error("Error when delete order id:" + currOrder);
                    }
                }
                break;
            }
        }
        while (parameterNames.hasMoreElements()) {
            if ("inOrder".equals(parameterNames.nextElement())) {
                String currOrder = req.getParameter("inOrder");
            }
        }

        Integer start = WebUtil.getNumberStartPage(req, session, LOG);
        try {
            List<Order> orders = new JdbcOrderDaoImpl().findAll();
            session.setAttribute(SessionParam.ORDER_LIST, orders);
            if (!orders.isEmpty()) {
                session.setAttribute(SessionParam.ORDER_COUNT, orders.size());
                User user = (User) session.getAttribute(SessionParam.USER);
                session.setAttribute(SessionParam.CAN_DELETE_ORDER, getCanDelete(user));
                session.setAttribute(SessionParam.CAN_DELETE_PRODUCT, getCanDeleteProduct(user));
            }
            List<User> userList = new JdbcUserDaoImpl().findAll();
            Map<Integer, String> usersMap = userList.stream()
                    .collect(Collectors.toMap(User::getId, User::getName));
            if (!usersMap.isEmpty()) {
                session.setAttribute(SessionParam.ALL_USERS, usersMap);
            }
        } catch (CashMachineException e) {
            LOG.error("JDBC Error", e);
        }
        return forward;
    }

    private boolean getCanDeleteProduct(User user) throws CashMachineException {
        if (user == null) {
            return false;
        }
        return new JdbcMenuDaoImpl().checkAccessToRoleId(user.getRoleId(), Permission.CAN_DELETE_PRODUCT);
    }

    private boolean getCanDelete(User user) throws CashMachineException {
        if (user == null) {
            return false;
        }
        return new JdbcMenuDaoImpl().checkAccessToRoleId(user.getRoleId(), Permission.CAN_DELETE_ORDER);
    }
}
