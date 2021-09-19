package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.ProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Product;
import com.epam.savenko.cashmachine.web.WebUtil;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.constant.SessionParam;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ProductsListCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ProductsListCommand.class);
    public static final int ROWS_IN_PAGE = SessionParam.ROWS_IN_PAGE;
    private static final long serialVersionUID = 5917893218719632767L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) {
        LOG.debug("Start command productslist");
        RoutePath forward = new RoutePath(Path.PAGE_PRODUCTS_LIST, RouteType.FORWARD);
        LOG.debug("Set redirect address: " + forward);
        int productCount;
        List<Product> products;
        HttpSession session = req.getSession();
        session.setAttribute("offset", ROWS_IN_PAGE);
        int currentPage = WebUtil.getNumberStartPage(req, session, LOG);

        ProductDao productDao = new JdbcProductDaoImpl();
        try {
            productCount = productDao.getCount();
            products = productDao.findPage(ROWS_IN_PAGE, ROWS_IN_PAGE * (currentPage-1));
            LOG.debug("Selected products" + products.size());
            LOG.debug("Selected product offset: " + (currentPage * ROWS_IN_PAGE));
            session.setAttribute("currentPage", currentPage);
            session.setAttribute("pages", productCount / ROWS_IN_PAGE + 1);
            session.setAttribute("productCount", productCount);
            session.setAttribute("products", products);
        } catch (CashMachineException e) {
            LOG.error("Error product list " + e.getMessage());
        }

        LOG.debug("Finished command productslist");
        return forward;
    }
}
