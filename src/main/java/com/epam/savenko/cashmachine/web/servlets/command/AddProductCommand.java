package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Product;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddProductCommand extends Command {

    private static final Logger LOG = Logger.getLogger(AddProductCommand.class);

    private static final long serialVersionUID = -1028222281221255065L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) {
        LOG.debug("Start command Add product");

        RoutePath forward = new RoutePath(Path.PAGE_BAD_LOGIN, RouteType.REDIRECT);
        HttpSession session = req.getSession();

        String productName = req.getParameter("productName");
        LOG.debug("Add product with name: " + productName);
        int idBrand = Integer.parseInt(req.getParameter("brand"));
        boolean weight = getTypeGoods(req.getParameter("RadiosW"));
        int quantity = getQuantity(req.getParameter("quantity"), weight);
        double price = Double.parseDouble(req.getParameter("price"));
        Product product = Product.newBuilder()
                .setName(productName)
                .setWeight(weight)
                .setQuantity(quantity)
                .setPrice(price)
                .setBrandId(idBrand)
                .build();
        try {
            new JdbcProductDaoImpl().insert(product);
            forward.setPath("controller?command=productslist");
            forward.setRouteType(RouteType.FORWARD);
        } catch (CashMachineException e) {
            LOG.error("Error add product ", e);
        }
        LOG.debug("Finished Add product");
        return forward;
    }

    private int getQuantity(String quantity, boolean weight) {
        return weight ? Integer.parseInt(quantity) * 1000 : Integer.parseInt(quantity);

    }

    private boolean getTypeGoods(String type) {
        return "RadiosW".equals(type);
    }
}
