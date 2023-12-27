package com.onm.ordersandnotificationsmanagement.accounts.models;

import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import lombok.*;
import java.util.ArrayList;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Account {
    private  String name;
    private  String email;
    private  String password;
    private  String phoneNumber;
    private  String language;
    private  Double balance;
    private ArrayList<Order> orders = new ArrayList<>();

    public void addNewOrder(Order order){
        orders.add(order);
    }
}