package com.onm.ordersandnotificationsmanagement.models;

import com.onm.ordersandnotificationsmanagement.repos.OrderRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccOrder {
    private Account account;
    private SimpleOrder simpleOrder;
}
