package com.onm.ordersandnotificationsmanagement.orders;
import com.onm.ordersandnotificationsmanagement.products.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter

public class SimpleOrder extends Order {
    ArrayList<Product> products;
    public SimpleOrder()
    {
        products = new ArrayList<>();
    }
}
