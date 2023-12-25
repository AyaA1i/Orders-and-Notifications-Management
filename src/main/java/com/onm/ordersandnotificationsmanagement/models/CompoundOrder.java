package com.onm.ordersandnotificationsmanagement.models;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter

public class CompoundOrder extends Order{
    ArrayList<SimpleOrder> simpleOrders;
    public CompoundOrder()
    {
        this.orderId = 0;
        this.orderFees = 0;
        this.shippingFees = 0;
        simpleOrders = new ArrayList<>();
    }
}
