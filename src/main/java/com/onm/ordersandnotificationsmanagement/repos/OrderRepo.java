package com.onm.ordersandnotificationsmanagement.repos;

import com.onm.ordersandnotificationsmanagement.models.Order;
import com.onm.ordersandnotificationsmanagement.services.OrderService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Setter
@Getter

@Repository
public class OrderRepo {
    private final ArrayList<Order> orders;
    public OrderRepo(){
        this.orders = new ArrayList<>();
    }
    public void add(Order order){
        orders.add(order);
    }
    public void remove(Order order){
        orders.remove(order);
    }
    public boolean search(Order order){
        return orders.contains(order);
    }
}
