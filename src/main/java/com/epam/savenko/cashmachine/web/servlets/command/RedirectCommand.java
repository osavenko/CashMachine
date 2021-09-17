package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.ProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Order;
import com.epam.savenko.cashmachine.model.Product;
import com.epam.savenko.cashmachine.model.view.OrderView;
import com.epam.savenko.cashmachine.web.WebUtil;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.List;

import static com.epam.savenko.cashmachine.web.constant.RequestParam.COMMAND;
import static com.epam.savenko.cashmachine.web.constant.SessionParam.*;

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
            OrderView orderView = (OrderView) session.getAttribute(ORDER_VIEW);
            if(orderView==null) {
                Order order = new Order();
                orderView = new OrderView(order);
                session.setAttribute(ORDER_VIEW, orderView);
            }
            forward.setPath(Path.PAGE_TO_ADD_CHECK);
            LOG.debug("Redirect page: " + Path.PAGE_TO_ADD_CHECK);
        }
        if ("choiceproduct".equals(command)) {
            LOG.debug("Start choiceproduct");
            HttpSession session = req.getSession();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String tov = parameterNames.nextElement();
                if (tov.startsWith("tov")) {
                    LOG.debug(tov);
                    try {
                        int tovId = Integer.parseInt(tov.substring(3));
                        LOG.debug("Choice product id " + tov + ":" + tovId);
                        boolean isWeight = Boolean.parseBoolean(req.getParameter("isWeight"));
                        int quantity;
                        if (!isWeight) {
                                quantity=Integer.parseInt(req.getParameter(tov));
                        }else {
                            quantity = (int) (Double.parseDouble(req.getParameter(tov))*1000);
                        }
                        LOG.debug("Choice product quantity " + quantity);
                        double price = Double.parseDouble(req.getParameter("currPrice"));
                        LOG.debug("Choice product price " + price);

                        OrderView orderView = (OrderView) session.getAttribute(ORDER_VIEW);
                        LOG.debug("After getting " + ORDER_VIEW);
                        if (orderView != null) {
                            LOG.debug(ORDER_VIEW+"is not null");
                            List<OrderView.ProductInOrderView> productInOrderViewList = orderView.getProductInOrderViewList();
                            LOG.debug("Length products in order:" + productInOrderViewList.size());
                            if (productInOrderViewList != null) {
                                orderView.addOrderProduct(tovId, quantity, price);
                            }
                        }
                    } catch (CashMachineException | NumberFormatException e) {
                        LOG.debug("Error quantity ", e);
                    }
                }
            }


            forward.setPath(Path.PAGE_CHOICE_PRODUCT);
            List<Product> products;
            session.setAttribute(OFFSET, ROWS_IN_PAGE);
            int currentPage = WebUtil.getNumberStartPage(req, session, LOG);
            try {
                ProductDao productDao = new JdbcProductDaoImpl();
                int productCount = productDao.getCount();
                products = productDao.findPage(ROWS_IN_PAGE, ROWS_IN_PAGE * (currentPage - 1));
                session.setAttribute(CURRENT_PAGE, currentPage);
                session.setAttribute(PAGES, productCount / ROWS_IN_PAGE + 1);
                session.setAttribute(PRODUCTS, products);
                session.setAttribute("currUrl", "controller?command=choiceproduct");
            } catch (CashMachineException e) {
                LOG.error("Error product list " + e.getMessage());
            }
            LOG.debug("Redirect page: " + Path.PAGE_CHOICE_PRODUCT);
        }
        return forward;
    }
}
