package com.onm.ordersandnotificationsmanagement.products.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Product.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
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
}
