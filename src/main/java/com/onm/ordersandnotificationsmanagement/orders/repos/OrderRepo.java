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
    /**
     * The constant ordersID.
     */
    public static int ordersID = 0;
}
