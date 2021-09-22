package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.LocaleProductDao;
import com.epam.savenko.cashmachine.dao.ProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcAppPropertiesDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleProductImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.*;
import com.epam.savenko.cashmachine.model.view.ProductView;
import com.epam.savenko.cashmachine.web.WebUtil;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.constant.SessionParam;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.epam.savenko.cashmachine.web.constant.SessionParam.USER;

public class ProductsListCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ProductsListCommand.class);
    private static final long serialVersionUID = 5917893218719632767L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) {
        int rows = SessionParam.ROWS_IN_PAGE;
        try {
            rows = Integer.parseInt(new JdbcAppPropertiesDao().getByName("lines").get().getValue());
        } catch (CashMachineException e) {
            LOG.error("Error when receive number row", e);
        }
        LOG.debug("Start command productslist");
        RoutePath forward = new RoutePath(Path.PAGE_PRODUCTS_LIST, RouteType.FORWARD);
        LOG.debug("Set redirect address: " + forward);
        HttpSession session = req.getSession();
        session.setAttribute("offset", rows);
        int currentPage = WebUtil.getNumberStartPage(req, session, LOG);

        ProductDao productDao = new JdbcProductDaoImpl();
        try {
            int productCount = productDao.getCount();
            List<Product> products = productDao.findPage(rows, rows * (currentPage - 1));
            List<ProductView> productViews = new ArrayList<>();
            User user = (User) session.getAttribute(USER);
            for (Product product : products) {
                String localeDescription = getLocale(product.getId(), user.getLocaleId());
                productViews.add(new ProductView(product, localeDescription));
            }
            LOG.debug("Selected products" + products.size());
            LOG.debug("Selected product offset: " + (currentPage * rows));
            session.setAttribute("currentPage", currentPage);
            /// убрал +1 исправить
            session.setAttribute("pages", productCount / rows + 1);
            session.setAttribute("productCount", productCount);
            session.setAttribute("products", productViews);
        } catch (CashMachineException e) {
            LOG.error("Error product list " + e.getMessage());
        }
        LOG.debug("Finished command productslist");
        return forward;
    }

    private String getLocale(int id, int localeId) {
        String description = "";
        LocaleProductDao localeProductDao = new JdbcLocaleProductImpl();
        try {
            Optional<LocaleProduct> descriptionProductByLocale = localeProductDao.findDescriptionProductByLocale(id, localeId);
            if(descriptionProductByLocale.isPresent()){
                description = descriptionProductByLocale.get().getDescription();
            }else {
                Optional<AppProperties> locale = new JdbcAppPropertiesDao().getByName("locale");
                if (locale.isPresent()){
                    descriptionProductByLocale = localeProductDao.findDescriptionProductByLocale(id, locale.get().getId());
                    if(descriptionProductByLocale.isPresent()){
                        description = descriptionProductByLocale.get().getDescription();
                    }
                }
            }
        } catch (CashMachineException e) {
            LOG.error("Error when receive locale for product id:" + id + ", localeId: " + localeId, e);
        }
        return description;
    }
}
