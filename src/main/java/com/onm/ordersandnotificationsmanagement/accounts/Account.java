package com.onm.ordersandnotificationsmanagement.accounts;

import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Account {
    private double balance;

    private String email;
    public Account(String email, double balance){
        this.email = email;
        this.balance = balance;
    }
}

