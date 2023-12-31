package com.onm.ordersandnotificationsmanagement.orders.models;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The type Order.
 */
@Setter
@Getter
public abstract class Order {

    /**
     * The Order id.
     */
    protected int orderId;
    /**
     * The Order fees.
     */
    protected double orderFees;
    /**
     * The Shipping fees.
     */
    protected double shippingFees;
    /**
     * The Date.
     */
    protected LocalDateTime date;
    /**
     * The Email.
     */
    protected String email;
    /**
     * The Cancelled.
     */
    protected boolean cancelled = false;
}
