package com.onm.ordersandnotificationsmanagement.orders;

import com.onm.ordersandnotificationsmanagement.accounts.Account;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public interface OrderService {

    public abstract void calcFees(Order order);
    public abstract boolean deductOrder(Order order, Account account);

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
