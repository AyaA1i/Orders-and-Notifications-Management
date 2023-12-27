package com.onm.ordersandnotificationsmanagement.orders.models;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Order {
    protected int orderId;
    protected double orderFees;
    protected double shippingFees;
}
