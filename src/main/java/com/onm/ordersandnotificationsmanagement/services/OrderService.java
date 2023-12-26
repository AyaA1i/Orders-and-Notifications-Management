package com.onm.ordersandnotificationsmanagement.services;

import com.onm.ordersandnotificationsmanagement.models.Account;
import com.onm.ordersandnotificationsmanagement.models.Order;
import com.onm.ordersandnotificationsmanagement.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.repos.AccountRepo;
import com.onm.ordersandnotificationsmanagement.repos.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;


@Service
public abstract class OrderService {
    // deduct -> order + shipping
    protected static final OrderRepo orderRepo = new OrderRepo();
    public abstract void calcFees(Order order);
    public abstract boolean placeOrder(HashMap<Order, Account> orderAccountHashMap);
    public static ArrayList<Order> listOrders(){
        /////for testing
        Order order1 = new SimpleOrder();
        order1.setOrderId(1);
        order1.setOrderFees(20);
        order1.setShippingFees(50);
        orderRepo.add(order1);
        Order order2 = new SimpleOrder();
        order2.setOrderId(2);
        order2.setOrderFees(20);
        order2.setShippingFees(50);
        orderRepo.add(order2);
        /////for testing
        return orderRepo.getOrders();
    }
}
