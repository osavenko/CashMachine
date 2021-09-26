package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Order;
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
import java.sql.Timestamp;
import java.time.Instant;

import static com.epam.savenko.cashmachine.web.constant.RequestParam.COMMAND;
import static com.epam.savenko.cashmachine.web.constant.SessionParam.ORDER_VIEW;

public class SaveCheckCommand extends Command {

    private static final long serialVersionUID = 1323375925616633137L;
    private static final Logger LOG = Logger.getLogger(SaveCheckCommand.class);

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_BAD_LOGIN, RouteType.FORWARD);
        String command = req.getParameter(COMMAND);
        LOG.debug("Redirect command: " + command);

        HttpSession session = req.getSession();
        OrderView orderView = (OrderView) session.getAttribute(ORDER_VIEW);
        Order order = orderView.getOrder();
        try {
            order.setAmount(new JdbcOrderProductDaoImpl().getSumByOrderId(order.getId()));
            order.setClosed(true);
            order.setClosedDateTime(Timestamp.from(Instant.now()));
            new JdbcOrderDaoImpl().update(order);
        } catch (CashMachineException e) {
            LOG.error("Error save order " + order, e);
        }
        forward.setPath("controller?command=orderslist");
        LOG.debug("Save check: " + orderView.getOrder().getId());
        session.setAttribute(ORDER_VIEW, null);

        return forward;
    }
}
