package com.onm.ordersandnotificationsmanagement.orders.models;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public abstract class Order {
    protected int orderId;
    protected double orderFees;
    protected double shippingFees;
    protected LocalDateTime date;
    protected String email;
}
