package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
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

public class DeleteProductInOrderCommand extends Command {

    private static final Logger LOG = Logger.getLogger(DeleteProductInOrderCommand.class);
    private static final long serialVersionUID = 8661167072206474083L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        LOG.debug("Start delete product from order");
        RoutePath forward = new RoutePath(Path.PAGE_ERROR, RouteType.REDIRECT);
        String deleteProductInOrder = req.getParameter("command");
        if ("deletepio".equals(deleteProductInOrder)) {
            HttpSession session = req.getSession();
            OrderView orderView = (OrderView) session.getAttribute(SessionParam.ORDER_VIEW);
            if (orderView != null) {
                LOG.error("DELETE product from order: " + orderView.getOrder().getId());
                String idProductToDelete = req.getParameter("delete");
                LOG.error("PRODUCT id: " + idProductToDelete);
                int id = Integer.parseInt(idProductToDelete);
                try {
                    new JdbcOrderProductDaoImpl().deleteProductFromOrder(orderView.getOrder().getId(), id);
                } catch (CashMachineException e) {
                    LOG.error("Error when delete products from orderId", e);
                }
                Command command = new EditCheckCommand();
                req.getSession().setAttribute("inOrder", orderView.getOrder().getId());
                forward = command.execute(req, res);
            }
        }
        return forward;
    }
}
