package com.onm.ordersandnotificationsmanagement.products.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Product.
 */
@Getter
@Setter
@NoArgsConstructor
public class Product {
    /**
     * The Serial number.
     * -- GETTER --
     * Gets serial number.
     *
     * @return the serial number
     */
    String serialNumber;
    /**
     * The Name.
     * -- GETTER --
     * Gets name.
     *
     * @return the name
     */
    String name;
    /**
     * The Vendor.
     * -- GETTER --
     * Gets vendor.
     *
     * @return the vendor
     */
    String vendor;
    /**
     * The Category.
     * -- GETTER --
     * Gets category.
     *
     * @return the category
     */
    Category category;
    /**
     * The Price.
     * -- GETTER --
     * Gets price.
     *
     * @return the price
     */
    double price;
    /**
     * The Available pieces number.
     */
    int availablePiecesNumber;

//    /**
//     * Instantiates a new Product.
//     *
//     * @param serialNumber the serial number
//     * @param name         the name
//     * @param vendor       the vendor
//     * @param category     the category
//     * @param price        the price
//     */
//    public Product(String serialNumber, String name, String vendor, Category category, double price, int availablePiecesNumber) {
//        this.serialNumber = serialNumber;
//        this.name = name;
//        this.vendor = vendor;
//        this.category = category;
//        this.price = price;
//        this.availablePiecesNumber = availablePiecesNumber;
//    }
}
