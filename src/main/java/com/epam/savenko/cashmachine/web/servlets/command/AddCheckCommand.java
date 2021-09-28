package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.OrderDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Order;
import com.epam.savenko.cashmachine.model.User;
import com.epam.savenko.cashmachine.model.view.OrderView;
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
import static com.epam.savenko.cashmachine.web.constant.SessionParam.*;

public class AddCheckCommand extends Command {

    private static final Logger LOG = Logger.getLogger(AddCheckCommand.class);
    private static final long serialVersionUID = 3154751263278421324L;


    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_BAD_LOGIN, RouteType.FORWARD);
        String command = req.getParameter(COMMAND);
        LOG.debug("Redirect command: " + command);

        HttpSession session = req.getSession();
        session.setAttribute(NEW_CHECK, true);
        OrderView orderView = (OrderView) session.getAttribute(ORDER_VIEW);
        if (orderView == null) {
            User user = (User) session.getAttribute(USER);
            Order order = Order.newOrder()
                    .setUserId(user.getId())
                    .setState(false)
                    .setCash(true)
                    .build();
            try {
                Optional<Order> newOrder = new JdbcOrderDaoImpl().insert(order);
                LOG.debug(newOrder.orElse(null));
                if(newOrder.isPresent()) {
                    orderView = new OrderView(newOrder.get());
                }
            } catch (CashMachineException e) {
                LOG.error("Error when was created new order", e);
            }
        } else {
            String changePay = req.getParameter("changePay");
            if ("checkpay".equals(changePay)) {
                LOG.error("checkpay :changePay: " + command);
                Order order = orderView.getOrder();
                order.setCash(isCashPay(req.getParameter("payment")));
                try {
                    OrderDao orderDao = new JdbcOrderDaoImpl();
                    orderDao.update(order);
                } catch (CashMachineException e) {
                    LOG.error("Error save order " + order, e);
                }
            }
        }
        session.setAttribute(ORDER_VIEW, orderView);
        forward.setPath(Path.PAGE_TO_ADD_CHECK);
        LOG.debug("Redirect page: " + Path.PAGE_TO_ADD_CHECK);

        return forward;
    }

    private boolean isCashPay(String payment) {
        return "cash".equals(payment);
    }
}