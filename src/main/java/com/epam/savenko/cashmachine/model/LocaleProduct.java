package com.epam.savenko.cashmachine.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class is linking description to product and locale.
 *
 * @author Oleh Savenko
 * @version 1.0
 * @see Locale
 * @see Product
 */
public class LocaleProduct implements Serializable {

    private static final long serialVersionUID = -3691850683333992842L;

    /**
     * field id
     */
    private int id;
    /**
     * field localeId
     */
    private int locateId;
    /**
     * field productId
     */
    private int productId;
    /**
     * field description
     */
    private String description;

    /**
     * Initializes a newly created LocaleProduct object.
     *
     * @see LocaleProduct#LocaleProduct(int, int, String)
     * @see LocaleProduct#LocaleProduct(int, int, int, String)
     */
    public LocaleProduct() {
    }

    /**
     * Initializes a newly created LocaleProduct object.
     * Sets three parameters <b>localeId</b>, <b>productId</b>, <b>description</b>
     *
     * @see LocaleProduct#LocaleProduct()
     * @see LocaleProduct#LocaleProduct(int, int, int, String)
     */
    public LocaleProduct(int locateId, int productId, String description) {
        this.locateId = locateId;
        this.productId = productId;
        this.description = description;
    }

    /**
     * Initializes a newly created LocaleProduct object.
     * Sets four parameters <b>id</b>, <b>localeId</b>, <b>productId</b>, <b>description</b>
     *
     * @see LocaleProduct#LocaleProduct(int, int, String)
     * @see LocaleProduct#LocaleProduct()
     */
    public LocaleProduct(int id, int locateId, int productId, String description) {
        this.id = id;
        this.locateId = locateId;
        this.productId = productId;
        this.description = description;
    }

    /**
     * Gets value field {@link LocaleProduct#id}
     *
     * @return id {@link LocaleProduct#id}
     */
    public int getId() {
        return id;
    }

    /**
     * Sets value field {@link LocaleProduct#id}
     *
     * @param id - id LocaleProduct {@link LocaleProduct#id}
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets value field {@link LocaleProduct#locateId}
     *
     * @return id of locale {@link LocaleProduct#locateId}
     * @see Locale
     */
    public int getLocateId() {
        return locateId;
    }

    /**
     * Sets value vield {@link LocaleProduct#locateId}
     *
     * @param locateId - id of locale
     * @see LocaleProduct#setLocateId(Locale)
     * @see Locale
     */
    public void setLocateId(int locateId) {
        this.locateId = locateId;
    }

    /**
     * Sets value vield {@link LocaleProduct#locateId}
     *
     * @param locale - object of class {@link Locale}
     * @see LocaleProduct#setLocateId(Locale)
     * @see Locale
     */
    public void setLocateId(Locale locale) {
        this.locateId = locale.getId();
    }

    /**
     * Gets value field {@link LocaleProduct#productId}
     *
     * @return id of product
     * @see Product
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets value field {@link LocaleProduct#productId}
     *
     * @param productId - id of product
     * @see Product
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Sets value field {@link LocaleProduct#productId} from class {@link Product}
     *
     * @param product - object of class {@link Product}
     * @see Product
     */
    public void setProductId(Product product) {
        this.productId = productId;
    }

    /**
     * Gets description
     *
     * @return description to {@link Product} for {@link Locale}
     * @see Locale
     * @see Product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description to {@link Product} for {@link Locale}
     *
     * @param description - description product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocaleProduct that = (LocaleProduct) o;
        return id == that.id && locateId == that.locateId && productId == that.productId && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, locateId, productId, description);
    }

    @Override
    public String toString() {
        return "LocateProduct{" +
                "id=" + id +
                ", locateId=" + locateId +
                ", productId=" + productId +
                ", description='" + description + '\'' +
                '}';
    }
}
