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
    @PostMapping(value = "/placeCompoundOrder/{email}")
    public ResponseEntity<Void> placeCompoundOrder(@RequestBody ArrayList<OrderAccount> orderAccounts,
                                                   @PathVariable(value = "email") String email){
        if(compoundOrderService.placeOrder(orderAccounts,email))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping(value = "/listOrders")
    public ResponseEntity<ArrayList<Order>> listOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(OrderService.listOrders());
    }
    @DeleteMapping(value = "/cancelSimpleOrder/{id}")
    public ArrayList<Order> cancelSimpleOrder( @PathVariable(value = "id") int id){
        return simpleOrderService.cancelOrder(id);
    }
    // Shipment cancel for bot(simple , compound)
    // cancel compound order
    // remove from notification queue // Aya
    // Statics -
    // Tokens
    // class diagram
    // notion

}
