package com.onm.ordersandnotificationsmanagement.orders.models;
import com.onm.ordersandnotificationsmanagement.products.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

/**
 * The type Simple order.
 */
@Setter
@Getter

public class SimpleOrder extends Order {
    /**
     * The Products.
     */
    ArrayList<Map.Entry<Product, Integer>> products;

    /**
     * Instantiates a new Simple order.
     */
    public SimpleOrder()
    {
        products = new ArrayList<>();
    }
}
