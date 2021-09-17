package com.epam.savenko.cashmachine.model.view;

import com.epam.savenko.cashmachine.dao.ProductDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcBrandDaoImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcLocaleProductImpl;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcProductDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
        productInOrderViewList = new ArrayList<>();
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

    public void addOrderProduct(int productId, int quantity, double price) throws CashMachineException {
        long countProducts = productInOrderViewList.stream()
                .filter(productInOrderView -> productInOrderView.getId() == productId)
                .count();
        if (countProducts > 0) {
            Iterator<ProductInOrderView> iterator = productInOrderViewList.iterator();
            while (iterator.hasNext()){
                ProductInOrderView next = iterator.next();
                if(next.getId()==productId){
                    next.quantity+=quantity;
                }
            }
        } else {
            Optional<Product> oProduct = new JdbcProductDaoImpl().findById(productId);
            if (oProduct.isPresent()) {
                Product p = oProduct.get();
                Optional<Brand> oBrand = new JdbcBrandDaoImpl().findById(p.getBrandId());
                String brandName = oBrand.isPresent() ? oBrand.get().getName() : "";
                ProductInOrderView product =
                        new ProductInOrderView(p.getId(), p.getName(), brandName, p.isWeight(), quantity, price);
                productInOrderViewList.add(product);
            }
        }

    }


    public static class ProductInOrderView {
        private int id;
        private String name;
        private String brandName;
        private boolean type;
        private int quantity;
        private double price;

        public ProductInOrderView(int id, String name, String brandName, boolean type, int quantity, double price) {
            this.id = id;
            this.name = name;
            this.brandName = brandName;
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

        public boolean isWeight() {
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
