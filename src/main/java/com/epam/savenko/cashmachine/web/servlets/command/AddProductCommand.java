package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.LocaleProductDao;
import com.epam.savenko.cashmachine.dao.ProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcAppPropertiesDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleProductImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.AppProperties;
import com.epam.savenko.cashmachine.model.Locale;
import com.epam.savenko.cashmachine.model.LocaleProduct;
import com.epam.savenko.cashmachine.model.Product;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AddProductCommand extends Command {

    private static final Logger LOG = Logger.getLogger(AddProductCommand.class);
    private static final long serialVersionUID = -1028222281221255065L;

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) {
        LOG.debug("Start command Add product");

        RoutePath forward = new RoutePath(Path.PAGE_BAD_LOGIN, RouteType.REDIRECT);
        HttpSession session = req.getSession();

        String productUpdate = req.getParameter("productUpdate");
        int id = 0;
        if (productUpdate != null) {
            try {
                id = Integer.parseInt(productUpdate);
            } catch (NumberFormatException e) {
                LOG.error("Error when work with product id: " + productUpdate, e);
            }
        }
        LOG.debug("Add/edit Checked id: " + id);
        String productName = req.getParameter("productName");
        LOG.debug("Add/edit productName: " + productName);
        String productDescription = req.getParameter("description");
        LOG.debug("Add/edit description: " + productDescription);
        LOG.error(req.getParameter("brand"));
        int idBrand = Integer.parseInt(req.getParameter("brand").trim());
        LOG.debug("Add/edit idBrand: " + idBrand);
        boolean weight = getTypeGoods(req.getParameter("typeRadios"));
        LOG.debug("Add/edit weight: " + weight);
        int quantity = 0;
        double price = Double.parseDouble(req.getParameter("price"));
        LOG.debug("Add/edit price: " + price);
        try {
            quantity = getQuantity(req.getParameter("quantity").trim(), weight);
        } catch (NumberFormatException e) {
            LOG.error("Error quantity " + weight, e);
        }
        if (quantity == 0) {
            return forward;
        }
        String localeName = req.getParameter("locale");
        Product product = Product.newBuilder()
                .setId(id)
                .setName(productName)
                .setWeight(weight)
                .setQuantity(quantity)
                .setPrice(price)
                .setBrandId(idBrand)
                .build();
        LOG.debug("Add/update product with name: " + productName);
        try {
            Optional<Product> newProduct = null;
            ProductDao productDao = new JdbcProductDaoImpl();
            if (product.getId() == 0) {
                newProduct = productDao.insert(product);
                LOG.debug("Add/update add new product: " + newProduct.get());
            } else {
                if (productDao.update(product)) {
                    newProduct = productDao.findById(product.getId());
                    LOG.debug("Add/update product was updated: " + newProduct.get());
                }
            }
            int localeId = new JdbcLocaleDaoImpl().findByName(new JdbcAppPropertiesDao().getByName("locale").get().getValue()).get().getId();
            LOG.error("default locale: "+localeId);
            LocaleProduct localeProduct = new LocaleProduct(localeId, newProduct.get().getId(), productDescription);
            LocaleProductDao localeProductDao = new JdbcLocaleProductImpl();
            Optional<LocaleProduct> descriptionProductByLocale = localeProductDao.findDescriptionProductByLocale(product.getId(), localeId);
            if (!descriptionProductByLocale.isPresent()) {
                localeProductDao.insert(localeProduct);
            } else {
                localeProduct.setId(descriptionProductByLocale.get().getId());
                localeProductDao.update(localeProduct);
            }
            forward.setPath("/controller?command=productslist");
            forward.setRouteType(RouteType.REDIRECT);
        } catch (CashMachineException e) {
            LOG.error("Error add product ", e);
        }
        LOG.debug("Finished Add product");
        return forward;
    }

    private int getQuantity(String quantity, boolean weight) {
        return weight ? (int) (Double.parseDouble(quantity) * 1000) : (int) Double.parseDouble(quantity);
    }

    private boolean getTypeGoods(String type) {
        return "0".equals(type);
    }
}
