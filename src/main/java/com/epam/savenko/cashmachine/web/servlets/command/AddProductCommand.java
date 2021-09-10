package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.Product;
import com.epam.savenko.cashmachine.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class AddProductCommand extends Command {

    private static final Logger LOG = Logger.getLogger(AddProductCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) {
        LOG.debug("Start command Add product");
        String forward = Path.PAGE_BAD_LOGIN;
        String name = req.getParameter("name");
        int idBrand = Integer.parseInt(req.getParameter("brand"));
        boolean weight = getTypeGoods(req.getParameter("RadiosW"));
        int quantity = getQuantity(req.getParameter("quantity"), weight);
        double price = Double.parseDouble(req.getParameter("price"));
        Product product = Product.newBuilder()
                .setName(name)
                .setWeight(weight)
                .setQuantity(quantity)
                .setPrice(price)
                .setBrandId(idBrand)
                .build();
        try {
            new JdbcProductDaoImpl().insert(product);
            forward = "controller?command=productslist";
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
