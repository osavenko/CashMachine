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
import java.util.ArrayList;
import java.util.List;

public class ProductsListCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ProductsListCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res){
        LOG.debug("Start command productslist");
        String forward = Path.PAGE_PRODUCTS_LIST;
        LOG.debug("Set redirect address: " + forward);
        int productCount = 0;
        List<Product> products = new ArrayList<>();
        HttpSession session = req.getSession();
        session.setAttribute("productCount",productCount);
        session.setAttribute("products", products);
        session.setAttribute("offset",5);
        Integer start = (Integer) session.getAttribute("startProsition");
        if (start == null) {
            start = 0;
        }else {
            String sStart = req.getParameter("page");
            try {
                start = Integer.parseInt(sStart);
            }catch (NumberFormatException e){
                LOG.debug("Error convert parameter page: "+e.getMessage());
            }
        }

        ProductDao productDao = new JdbcProductDaoImpl();
        try {
            productCount = productDao.productCount();
            products = productDao.findPage(5,5*start);
        } catch (CashMachineException e) {
            LOG.error("Error product list "+e.getMessage());
        }

        LOG.debug("Finished command productslist");
        return forward;
    }
}
