package com.onm.ordersandnotificationsmanagement.orders.models;

import ch.qos.logback.core.joran.sanity.Pair;
import com.onm.ordersandnotificationsmanagement.products.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Map;

/**
 * The type Order account.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class OrderAccount {
    private String accEmail;
    private ArrayList<Map.Entry<String, Integer>> prodSerialNum = new ArrayList<>();
}
