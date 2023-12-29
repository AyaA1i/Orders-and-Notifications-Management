package com.onm.ordersandnotificationsmanagement.orders.models;
import ch.qos.logback.core.joran.sanity.Pair;
import com.onm.ordersandnotificationsmanagement.products.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * The type Simple order.
 */
@Setter
@Getter

public class SimpleOrder extends Order {
    /**
     * The Products.
     */
    ArrayList<Pair<Product, Integer>> products;

    /**
     * Instantiates a new Simple order.
     */
    public SimpleOrder()
    {
        products = new ArrayList<>();
    }
}
