package com.onm.ordersandnotificationsmanagement.products.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
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
    @DecimalMin(value = "0")
    double price;
    /**
     * The Available pieces number.
     */
    @DecimalMin(value = "0")
    int availablePiecesNumber;
    @Override
    public String toString() {
        return "Product{serialNumber='" + serialNumber + "', name='" + name + "', price=" + price + "}";
    }

}
