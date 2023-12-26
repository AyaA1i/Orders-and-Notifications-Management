package com.onm.ordersandnotificationsmanagement.orders;

import com.onm.ordersandnotificationsmanagement.orders.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Setter
@Getter

@Repository
public class OrderRepo {
    @Getter
    private static final ArrayList<Order> orders = new ArrayList<>();
    public static void add(Order order){
        orders.add(order);
    }
    public void remove(Order order){
        orders.remove(order);
    }
    public boolean searchByOrder(Order order){
        return orders.contains(order);
    }
    public Order searchById(int id){
        for(Order order: orders){
            if(order.getOrderId() == id)
                return order;
        }
        return null;
    }
}
