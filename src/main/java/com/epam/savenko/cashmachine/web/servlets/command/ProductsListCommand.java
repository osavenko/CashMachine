package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.web.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductsListCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ProductsListCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res){
        LOG.debug("Start command productslist");
        String forward = Path.PAGE_PRODUCTS_LIST;

        LOG.debug("Set redirect address: " + forward);

        LOG.debug("Finished command productslist");
        return forward;
    }
}
