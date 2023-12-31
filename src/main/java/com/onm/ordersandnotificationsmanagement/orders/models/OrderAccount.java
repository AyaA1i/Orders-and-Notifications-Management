package com.onm.ordersandnotificationsmanagement.orders.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Order account.
 */
@Setter
@Getter
@Component
@NoArgsConstructor
public class OrderAccount {
    private String accEmail;
    private List<Map.Entry<String, Integer>> prodSerialNum= new ArrayList<>();
}
