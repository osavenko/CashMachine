package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.ProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Product;
import com.epam.savenko.cashmachine.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ProductsListCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ProductsListCommand.class);
    public static final int ROWS_IN_PAGE = 5;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        LOG.debug("Start command productslist");
        String forward = Path.PAGE_PRODUCTS_LIST;
        LOG.debug("Set redirect address: " + forward);
        int productCount;
        List<Product> products;
        HttpSession session = req.getSession();
        session.setAttribute("offset", ROWS_IN_PAGE);
        Integer start = (Integer) session.getAttribute("startPosition");
        if (start == null) {
            start = 0;
        } else {
            String sStart = req.getParameter("page");
            try {
                start = Integer.parseInt(sStart);
            } catch (NumberFormatException e) {
                LOG.error("Error convert parameter page: " + e.getMessage());
            }
        }

        ProductDao productDao = new JdbcProductDaoImpl();
        try {
            productCount = productDao.getCount();
            products = productDao.findPage(ROWS_IN_PAGE, ROWS_IN_PAGE * start);
            LOG.debug("Selected products" + products.size());
            LOG.debug("Selected product offset: " + (start * ROWS_IN_PAGE));
            session.setAttribute("productCount", productCount);
            session.setAttribute("products", products);
        } catch (CashMachineException e) {
            LOG.error("Error product list " + e.getMessage());
        }

        LOG.debug("Finished command productslist");
        return forward;
    }
}
