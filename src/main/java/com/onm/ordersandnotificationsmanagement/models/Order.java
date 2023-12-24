package com.onm.ordersandnotificationsmanagement.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter
@NoArgsConstructor
public abstract class Order {
    protected int orderId;
    protected double orderFees;
    protected double shippingFees;
}
