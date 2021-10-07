package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.jdbc.JdbcAppPropertiesDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleProductImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.AppProperties;
import com.epam.savenko.cashmachine.model.Locale;
import com.epam.savenko.cashmachine.model.LocaleProduct;
import com.epam.savenko.cashmachine.model.Product;
import com.epam.savenko.cashmachine.model.view.ProductDescriptionView;
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
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.web.constant.SessionParam.DEFAULT_LOCALE;

public class EditProductCommand extends Command {

    private static final Logger LOG = Logger.getLogger(EditProductCommand.class);

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_TO_ADD_PRODUCTS, RouteType.FORWARD);
        HttpSession session = req.getSession();
        String currentid = req.getParameter("currentid");
        if (currentid != null) {
            try {
                int id = Integer.parseInt(currentid);
                Optional<Product> product = new JdbcProductDaoImpl().findById(id);
                if (!product.isPresent()) {
                    throw new CashMachineException("Product not found");
                }
                session.setAttribute("product", product.get());

                Optional<AppProperties> appLocale = new JdbcAppPropertiesDao().getByName("locale");
                String defaultLocale = "";
                if (appLocale.isPresent()) {
                    defaultLocale = appLocale.get().getValue();
                }
                session.setAttribute(DEFAULT_LOCALE, defaultLocale);
                session.setAttribute("id", id);
                if (id > 0) {
                    if (!defaultLocale.isEmpty()) {
                        Optional<Locale> byName = new JdbcLocaleDaoImpl().findByName(defaultLocale);
                        if (byName.isPresent()) {
                            Optional<LocaleProduct> descriptionProductByLocale = new JdbcLocaleProductImpl().findDescriptionProductByLocale(product.get().getId(), byName.get().getId());
                            if (descriptionProductByLocale.isPresent()) {
                                session.setAttribute("localeDescription", descriptionProductByLocale.get().getDescription());
                            }
                        }
                    }
                }
            } catch (NumberFormatException | CashMachineException e) {
                LOG.error("Error when parse product id: " + currentid, e);
                forward.setPath(Path.PAGE_ERROR);
            }
        }
        return forward;
    }
}
