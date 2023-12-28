package com.onm.ordersandnotificationsmanagement.orders.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.orders.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.products.services.ProductService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;


@Service
public interface OrderService {
    OrderRepo orderRepo = new OrderRepo();
    ProductService productService = new ProductService();

    Integer ALLOWED_DURATION = 60;

    void calcOrderFees(Order order);
    void calcShippingFees(Order order);
    boolean shipOrder(Order order, Account account);
    boolean deductOrder(Order order, Account account);
    void cancelOrder(Order order);
    void cancelOrderShipment(Order order);
    static boolean cancel(int orderId, boolean ship){
        Order order = orderRepo.searchById(orderId);
        Duration duration = Duration.between(order.getDate(),java.time.LocalDateTime.now());
        if(duration.toSeconds() > ALLOWED_DURATION){
            return false;
        }
        if(order instanceof SimpleOrder)
        {
            SimpleOrderService simpleOrderService = new SimpleOrderService();
            if(ship)
            {
               simpleOrderService.cancelOrderShipment(order);
            }
            else
                simpleOrderService.cancelOrder(order);
            return true;
        }
        CompoundOrderService compoundOrderService = new CompoundOrderService();
        if(ship)
        {
            compoundOrderService.cancelOrderShipment(order);
        }
        else
            compoundOrderService.cancelOrder(order);
        return true;
    }
    static ArrayList<Order> listOrders() {
        return OrderRepo.getOrders();
    }
}
