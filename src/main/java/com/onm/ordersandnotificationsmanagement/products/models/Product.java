package com.onm.ordersandnotificationsmanagement.products.models;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Product.
 */
@Getter
@Setter
public class Product {
    /**
     * The Serial number.
     * -- GETTER --
     *  Gets serial number.
     *
     * @return the serial number

     */
    String serialNumber;
    /**
     * The Name.
     * -- GETTER --
     *  Gets name.
     *
     * @return the name

     */
    String name;
    /**
     * The Vendor.
     * -- GETTER --
     *  Gets vendor.
     *
     * @return the vendor

     */
    String vendor;
    /**
     * The Category.
     * -- GETTER --
     *  Gets category.
     *
     * @return the category

     */
    String category;
    /**
     * The Price.
     * -- GETTER --
     *  Gets price.
     *
     * @return the price

     */
    double price;

    /**
     * Instantiates a new Product.
     *
     * @param serialNumber the serial number
     * @param name         the name
     * @param vendor       the vendor
     * @param category     the category
     * @param price        the price
     */
    public Product(String serialNumber, String name, String vendor, String category, double price) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.vendor = vendor;
        this.category = category;
        this.price = price;
    }

    /**
     * Sets serial number.
     *
     * @param serialNumber the serial number
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets vendor.
     *
     * @param vendor the vendor
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
