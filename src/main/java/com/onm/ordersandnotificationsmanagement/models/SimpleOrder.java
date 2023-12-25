package com.onm.ordersandnotificationsmanagement.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter

public class SimpleOrder extends Order{
    ArrayList<Product> products;
    public SimpleOrder()
    {
        products = new ArrayList<>();
    }
}
