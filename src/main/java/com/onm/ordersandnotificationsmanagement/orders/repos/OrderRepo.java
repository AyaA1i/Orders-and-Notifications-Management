package com.onm.ordersandnotificationsmanagement.orders.repos;


import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;
@Service
public class OrderRepo {
    public static final Queue<Order> orders = new ArrayDeque<>();

}