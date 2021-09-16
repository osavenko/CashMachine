package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.model.Order;
import com.epam.savenko.cashmachine.model.view.OrderView;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.savenko.cashmachine.web.constant.RequestParam.COMMAND;
import static com.epam.savenko.cashmachine.web.constant.SessionParam.NEW_CHECK;
import static com.epam.savenko.cashmachine.web.constant.SessionParam.ORDER_VIEW;

public class RedirectCommand extends Command {
    private static final Logger LOG = Logger.getLogger(RedirectCommand.class);


    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) {
        RoutePath forward = new RoutePath(Path.PAGE_BAD_LOGIN, RouteType.FORWARD);
        String errorMessage = "Error redirect";
        String command = req.getParameter(COMMAND);
        LOG.debug("Redirect command: " + command);
        if ("addproductpage".equals(command)) {
            forward.setPath(Path.PAGE_TO_ADD_PRODUCTS);
            LOG.debug("Redirect page: " + Path.PAGE_TO_ADD_PRODUCTS);
        }
        if ("addcheck".equals(command)) {
            HttpSession session = req.getSession();
            session.setAttribute(NEW_CHECK, "yes");
            Order order = new Order();
            OrderView orderView = new OrderView(order);
            session.setAttribute(ORDER_VIEW, orderView);
            forward.setPath(Path.PAGE_TO_ADD_CHECK);
            LOG.debug("Redirect page: " + Path.PAGE_TO_ADD_CHECK);
        }
        return forward;
    }
}
