package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.OrderDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Order;
import com.epam.savenko.cashmachine.model.view.OrderView;
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
import java.util.Optional;

public class EditCheckCommand extends Command {

    private static final Logger LOG = Logger.getLogger(EditCheckCommand.class);
    private static final long serialVersionUID = -5515692747517058591L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_ERROR, RouteType.FORWARD);
        HttpSession session = req.getSession();
        String inOrder = req.getParameter("inOrder");
        int orderId = 0;
        if (inOrder!=null) {
            try {
                orderId = Integer.parseInt(inOrder);
            } catch (NumberFormatException e) {
                LOG.error("Error order id: " + inOrder);
                return forward;
            }
        }else{
            orderId = (int) session.getAttribute("inOrder");
        }

        if (orderId > 0) {
            Optional<Order> order;
            try {
                order = new JdbcOrderDaoImpl().findById(orderId);
            } catch (CashMachineException e) {
                LOG.error("Error when  receive order bu ID: " + orderId, e);
                return forward;
            }
            if (order.isPresent()) {
                OrderView orderView = new OrderView(order.get());
                try {
                    orderView.setProductInOrderViewList(new JdbcOrderProductDaoImpl().getProductInOrderViewById(order.get().getId()));
                } catch (CashMachineException e) {
                    LOG.error("Error when receive product to order by orderId: " + orderId, e);
                    return forward;
                }

                session.setAttribute(SessionParam.ORDER_VIEW, orderView);
                session.setAttribute(SessionParam.NEW_CHECK, false);
                forward.setPath(Path.PAGE_TO_ADD_CHECK);
                forward.setRouteType(RouteType.FORWARD);
            }
        }
        return forward;
    }
}
