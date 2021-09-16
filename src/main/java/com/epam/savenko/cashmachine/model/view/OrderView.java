package com.epam.savenko.cashmachine.model.view;

import com.epam.savenko.cashmachine.dao.ProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcBrandDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleProductImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.*;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class OrderView implements Serializable {

    private static final long serialVersionUID = 2489483659859659232L;

    private Order order;
    private List<ProductInOrderView> productInOrderViewList;


    public OrderView() {
    }

    public OrderView(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<ProductInOrderView> getProductInOrderViewList() {
        return productInOrderViewList;
    }

    public void addOrderProduct(int productId, int quantity, double price, int localeId) throws CashMachineException {
        Optional<Product> oProduct = new JdbcProductDaoImpl().findById(productId);
        if (oProduct.isPresent()) {
            Product p = oProduct.get();
            Optional<LocaleProduct> oLocaleProduct = new JdbcLocaleProductImpl().findDescriptionProductByLocale(p.getId(), localeId);
            Optional<Brand> oBrand = new JdbcBrandDaoImpl().findById(p.getBrandId());
            String description = oLocaleProduct.isPresent() ? oLocaleProduct.get().getDescription() : "";
            String brandName = oBrand.isPresent() ? oBrand.get().getName() : "";
            ProductInOrderView product =
                    new ProductInOrderView(p.getId(), p.getName(), brandName, description, p.isWeight(), quantity, price);
            productInOrderViewList.add(product);
        }
    }

    public static class ProductInOrderView {
        private int id;
        private String name;
        private String brandName;
        private String description;
        private boolean type;
        private int quantity;
        private double price;

        public ProductInOrderView(int id, String name, String brandName, String description, boolean type, int quantity, double price) {
            this.id = id;
            this.name = name;
            this.brandName = brandName;
            this.description = description;
            this.type = type;
            this.quantity = quantity;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getBrandName() {
            return brandName;
        }

        public String getDescription() {
            return description;
        }

        public boolean isType() {
            return type;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getPrice() {
            return price;
        }
    }
}
