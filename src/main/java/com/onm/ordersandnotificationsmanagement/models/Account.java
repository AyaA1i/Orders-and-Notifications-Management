package com.onm.ordersandnotificationsmanagement.models;

import lombok.Getter;

import lombok.Setter;

@Setter
@Getter
public class Account {
    private double balance;
    private int id;
    public Account(int id, double balance){
        this.id = id;
        this.balance = balance;
    }
}

