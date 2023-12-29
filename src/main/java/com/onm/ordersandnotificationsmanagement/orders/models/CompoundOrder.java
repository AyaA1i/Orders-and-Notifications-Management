package com.onm.ordersandnotificationsmanagement.orders.models;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Compound order.
 */
@Setter
@Getter

public class CompoundOrder extends Order {
    /**
     * The Simple orders.
     */
    ArrayList<SimpleOrder> simpleOrders;

    /**
     * Instantiates a new Compound order.
     */
    public CompoundOrder()
    {
        this.setDate(LocalDateTime.now());
        this.orderFees = 0;
        this.shippingFees = 0;
        simpleOrders = new ArrayList<>();
    }
}
