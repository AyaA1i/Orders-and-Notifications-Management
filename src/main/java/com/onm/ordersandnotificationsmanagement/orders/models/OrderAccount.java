package com.onm.ordersandnotificationsmanagement.orders.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * The type Order account.
 */
@Setter
@Getter
@Component
public class OrderAccount {
    private String accEmail;
    private ArrayList<String> prodSerialNum;
}
