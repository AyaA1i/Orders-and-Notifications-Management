package com.onm.ordersandnotificationsmanagement.orders.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.orders.models.SimpleOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public interface OrderService {
    Integer ALLOWED_DURATION = 60;

    void calcOrderFees(Order order);
    void calcShippingFees(Order order);
    boolean shipOrder(Order order, Account account);
    boolean deductOrder(Order order, Account account);
    ArrayList<Order> cancelOrder(int orderId);

    public static ArrayList<Order> listOrders() {
        /////for testing
        Order order1 = new SimpleOrder();
        order1.setOrderId(1);
        order1.setOrderFees(20);
        order1.setShippingFees(50);
        OrderRepo.add(order1);
        Order order2 = new SimpleOrder();
        order2.setOrderId(2);
        order2.setOrderFees(20);
        order2.setShippingFees(50);
        OrderRepo.add(order2);
        /////for testing
        return OrderRepo.getOrders();
    }
}
