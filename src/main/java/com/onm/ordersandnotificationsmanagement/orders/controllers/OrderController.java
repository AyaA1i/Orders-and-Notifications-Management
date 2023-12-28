package com.onm.ordersandnotificationsmanagement.orders.controllers;

import com.onm.ordersandnotificationsmanagement.orders.*;
import com.onm.ordersandnotificationsmanagement.orders.services.CompoundOrderService;
import com.onm.ordersandnotificationsmanagement.orders.services.OrderService;
import com.onm.ordersandnotificationsmanagement.orders.services.SimpleOrderService;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
public class OrderController {
    private final SimpleOrderService simpleOrderService;
    private final CompoundOrderService compoundOrderService;
    @Autowired
    public OrderController(SimpleOrderService simpleOrderService, CompoundOrderService compoundOrderService) {
        this.simpleOrderService = simpleOrderService;
        this.compoundOrderService = compoundOrderService;
    }
    @PostMapping(value = "/placeSimpleOrder")
    public ResponseEntity<Void> placeSimpleOrder(@RequestBody OrderAccount orderAccount){

        if(simpleOrderService.placeOrder(orderAccount))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping(value = "/placeCompoundOrder")
    public ResponseEntity<Void> placeCompoundOrder(@RequestBody ArrayList<OrderAccount> orderAccounts){
        if(compoundOrderService.placeOrder(orderAccounts))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping(value = "/listOrders")
    public ResponseEntity<ArrayList<Order>> listOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(OrderService.listOrders());
    }
}
