package com.epam.savenko.cashmachine.dao;

public class Fields {

    private Fields() {
    }

    public static class Brand {
        private Brand() {
        }

        public static final String ID = "id";
        public static final String NAME = "name";
    }

    public static class Locale {
        private Locale() {
        }

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    }

    public static class LocaleProduct {
        private LocaleProduct() {
        }

        public static final String ID = "id";
        public static final String LOCALE_ID = "locale_id";
        public static final String PRODUCT_ID = "product_id";
        public static final String DESCRIPTION = "description";
    }

    public static class Order {
        private Order() {
        }

        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String AMOUNT = "amount";
        public static final String CLOSED = "closed";
        public static final String CASH = "cash";
        public static final String ORDER_DATETIME = "order_datetime";
        public static final String CLOSED_DATETIME = "closed_datetime";
    }

    public static class OrderProduct {
        private OrderProduct() {
        }

        public static final String ID = "id";
        public static final String PRICE = "price";
        public static final String PRODUCT_ID = "product_id";
        public static final String ORDER_ID = "order_id";
        public static final String QUANTITY = "quantity";
    }

    public static class Product {
        private Product() {
        }

        public static final String ID = "id";
        public static final String BRAND_ID = "brand_id";
        public static final String NAME = "name";
        public static final String PRICE = "price";
        public static final String QUANTITY = "quantity";
        public static final String WEIGHT = "weight";
    }

    public static class Role {
        private Role() {
        }

        public static final String ID = "id";
        public static final String NAME = "name";
    }

    public static class User {
        private User() {
        }

        public static final String ACTIVATED = "activated";
        public static final String NAME = "name";
        public static final String HASH = "hash";
        public static final String ROLE_ID = "role_id";
        public static final String ID = "id";
        public static final String LOCATE_ID = "locale_id";
    }

    public static class UserDetails {
        private UserDetails() {
        }

        public static final String ID = "id";
        public static final String FULLNAME = "fullname";
        public static final String USER_ID = "user_id";
    }

    public static class MenuItem {
        private MenuItem() {
        }

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String URL = "url";
    }
}
