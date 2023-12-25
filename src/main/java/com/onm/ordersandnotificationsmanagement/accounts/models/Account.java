package com.onm.ordersandnotificationsmanagement.accounts.models;

import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import lombok.*;
import java.util.ArrayList;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Account {
    private final String name;
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final Double balance;
    private ArrayList<Order> orders = new ArrayList<>();
}