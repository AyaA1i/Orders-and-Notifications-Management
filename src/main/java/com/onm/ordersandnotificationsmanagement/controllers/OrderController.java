package com.onm.ordersandnotificationsmanagement.controllers;

import com.onm.ordersandnotificationsmanagement.models.Account;
import com.onm.ordersandnotificationsmanagement.models.Order;
import com.onm.ordersandnotificationsmanagement.services.OrderService;
import com.onm.ordersandnotificationsmanagement.services.SimpleOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class OrderController {
    private OrderService orderService;
    @PostMapping(value = "/place-simple-order")
    public ResponseEntity<Void> placeOrder(@RequestBody HashMap<Order, Account> orderAccountHashMap){
        ((SimpleOrderService)orderService).placeOrder(orderAccountHashMap);
    return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping(value = "/all-orders")
    public ResponseEntity<ArrayList<Order>> listOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(OrderService.listOrders());
    }
}
