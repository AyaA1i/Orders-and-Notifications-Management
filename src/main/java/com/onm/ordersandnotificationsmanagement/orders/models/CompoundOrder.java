package com.onm.ordersandnotificationsmanagement.orders.models;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
@Setter
@Getter

public class CompoundOrder extends Order {
    ArrayList<SimpleOrder> simpleOrders;
    public CompoundOrder()
    {
        this.setDate(LocalDateTime.now());
        this.orderFees = 0;
        this.shippingFees = 0;
        simpleOrders = new ArrayList<>();
    }
}
