package com.onm.ordersandnotificationsmanagement.orders.models;

import com.onm.ordersandnotificationsmanagement.orders.models.Order;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SimpleOrder extends Order{
    public SimpleOrder(int id) {
        this.setId(id);
    }

}
