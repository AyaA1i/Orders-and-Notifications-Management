package com.onm.ordersandnotificationsmanagement.orders.models;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public abstract class Order {

    protected int orderId;
    protected double orderFees;
    protected double shippingFees;
    protected LocalDateTime date;
    protected String email;
}
