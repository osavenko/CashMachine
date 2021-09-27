package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.OrderProductDao;
import com.epam.savenko.cashmachine.dao.ProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderProductDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.OrderProduct;
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
import java.util.*;

import static com.epam.savenko.cashmachine.web.constant.RequestParam.COMMAND;
import static com.epam.savenko.cashmachine.web.constant.SessionParam.*;

public class ChoiceProductCommand extends Command {
    private static final Logger LOG = Logger.getLogger(ChoiceProductCommand.class);
    private static final long serialVersionUID = 8135816868346486860L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) {
        RoutePath forward = new RoutePath(Path.PAGE_BAD_LOGIN, RouteType.FORWARD);
        String command = req.getParameter(COMMAND);
        LOG.debug("Redirect command: " + command);

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
                        quantity = Integer.parseInt(req.getParameter(tov));
                    } else {
                        quantity = (int) (Double.parseDouble(req.getParameter(tov)) * 1000);
                    }
                    LOG.debug("Choice product quantity " + quantity);
                    double price = Double.parseDouble(req.getParameter("currPrice"));
                    LOG.debug("Choice product price " + price);

                    OrderView orderView = (OrderView) session.getAttribute(ORDER_VIEW);
                    LOG.debug("After getting " + ORDER_VIEW);
                    if (orderView != null) {
                        LOG.debug(ORDER_VIEW + "is not null");
                        //add product
                        OrderProduct orderProduct = OrderProduct.newBuilder()
                                .setOrderId(orderView.getOrder().getId())
                                .setProductId(tovId)
                                .setQuantity(quantity)
                                .setPrice(price)
                                .build();
                        OrderProductDao orderProductDao = new JdbcOrderProductDaoImpl();
                        orderProductDao.insert(orderProduct);
                        orderView.setProductInOrderViewList(orderProductDao.getProductInOrderViewById(orderView.getOrder().getId()));
                        List<OrderView.ProductInOrderView> productInOrderViewList = orderView.getProductInOrderViewList();
                        new JdbcProductDaoImpl().changeQuantityProduct(tovId, -quantity);
                        LOG.debug("Length products in order:" + productInOrderViewList.size());
                    }
                } catch (CashMachineException | NumberFormatException e) {
                    LOG.debug("Error quantity ", e);
                }
            }
        }
        forward.setPath(Path.PAGE_CHOICE_PRODUCT);
        List<Product> products = new ArrayList<>();
        session.setAttribute(OFFSET, ROWS_IN_PAGE);
        int currentPage = WebUtil.getNumberStartPage(req, session, LOG);
        try {
            String search = req.getParameter("search");
            ProductDao productDao = new JdbcProductDaoImpl();
            int productCount = 0;
            if (search != null && !search.isEmpty()) {
                int code = 0;
                try {
                    code = Integer.parseInt(search);
                } catch (NumberFormatException e) {
                    LOG.error("Search string not code");
                }
                if (search.length() > 0) {
                    products = productDao.findSearch(code, search, ROWS_IN_PAGE, ROWS_IN_PAGE * (currentPage - 1));
                    productCount = productDao.getCountWhenSearch(code, search);
                    session.setAttribute("currUrl", "controller?command=choiceproduct&search=" + search);
                }
            } else {
                products = productDao.findPage(ROWS_IN_PAGE, ROWS_IN_PAGE * (currentPage - 1));
                productCount = productDao.getCount();
                session.setAttribute("currUrl", "controller?command=choiceproduct");
            }

            session.setAttribute("productCount", productCount);

            session.setAttribute(CURRENT_PAGE, currentPage);
            session.setAttribute(PAGES, (productCount + ROWS_IN_PAGE) / ROWS_IN_PAGE);
            OrderView orderView = (OrderView) session.getAttribute(ORDER_VIEW);
            Map<Product, Integer> mapProducts = new LinkedHashMap<>();
            if (orderView != null) {
                List<OrderView.ProductInOrderView> productInOrderViewList = orderView.getProductInOrderViewList();
                for (Product product : products) {
                    int quantityInOrder = containsProduct(product, productInOrderViewList);
                    mapProducts.put(product, quantityInOrder);
                }
            }
            session.setAttribute(PRODUCTS, mapProducts);
        } catch (CashMachineException e) {
            LOG.error("Error product list " + e.getMessage());
        }

        LOG.debug("Redirect page: " + Path.PAGE_CHOICE_PRODUCT);
        return forward;
    }

    private int containsProduct(Product product, List<OrderView.ProductInOrderView> productInOrderViewList) {
        for (OrderView.ProductInOrderView productInOrderView : productInOrderViewList) {
            if (productInOrderView.getId() == product.getId()) {
                return productInOrderView.getQuantity();
            }
        }
        return 0;
    }
}
