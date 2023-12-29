package com.onm.ordersandnotificationsmanagement.orders.repos;

import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * The type Order repo.
 */
@Setter
@Getter

@Repository
public class OrderRepo {
    @Getter
    private static final ArrayList<Order> orders = new ArrayList<>();
    private int noOfOrders = 0;

    /**
     * Add.
     *
     * @param order the order
     */
    public static void add(Order order){
        orders.add(order);
    }

    /**
     * Remove.
     *
     * @param order the order
     */
    public void remove(Order order){
        orders.remove(order);
    }

    /**
     * Search by order boolean.
     *
     * @param order the order
     * @return the boolean
     */
    public boolean searchByOrder(Order order){
        return orders.contains(order);
    }

    /**
     * Search by id order.
     *
     * @param id the id
     * @return the order
     */
    public Order searchById(int id){
        for(Order order: orders){
            if(order.getOrderId() == id)
                return order;
        }
        return null;
    }
}
