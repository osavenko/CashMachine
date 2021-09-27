package com.epam.savenko.cashmachine.web.servlets.command;

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

import static com.epam.savenko.cashmachine.web.constant.RequestParam.COMMAND;
import static com.epam.savenko.cashmachine.web.constant.SessionParam.ORDER_VIEW;

public class CancelCheckCommand extends Command {

    private static final long serialVersionUID = 1614275666914257675L;
    private static final Logger LOG = Logger.getLogger(CancelCheckCommand.class);

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath("/controller?command=orderslist", RouteType.REDIRECT);
        String command = req.getParameter(COMMAND);
        LOG.debug("Redirect command: " + command);
        HttpSession session = req.getSession();
        OrderView orderView = (OrderView) session.getAttribute(ORDER_VIEW);
        LOG.debug("Cancel check: " + orderView.getOrder().getId());
        session.setAttribute(ORDER_VIEW, null);
        return forward;
    }
}
