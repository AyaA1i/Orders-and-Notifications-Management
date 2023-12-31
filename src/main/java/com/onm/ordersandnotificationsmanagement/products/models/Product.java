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
     */
    String serialNumber;
    /**
     * The Name.
     */
    String name;
    /**
     * The Vendor.
     */
    String vendor;
    /**
     * The Category.
     */
    Category category;
    /**
     * The Price.
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
