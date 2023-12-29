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

@RequestMapping("/order")

/**
 * The type Order controller.
 */
@RestController
public class OrderController {
    private final SimpleOrderService simpleOrderService;
    private final CompoundOrderService compoundOrderService;

    /**
     * Instantiates a new Order controller.
     *
     * @param simpleOrderService   the simple order service
     * @param compoundOrderService the compound order service
     */
    @Autowired
    public OrderController(SimpleOrderService simpleOrderService, CompoundOrderService compoundOrderService) {
        this.simpleOrderService = simpleOrderService;
        this.compoundOrderService = compoundOrderService;
    }

    /**
     * Place simple order response entity.
     *
     * @param orderAccount the order account
     * @return the response entity
     */
    @PostMapping(value = "/placeSimpleOrder")
    public ResponseEntity<Void> placeSimpleOrder(@RequestBody OrderAccount orderAccount){

        if(simpleOrderService.placeOrder(orderAccount))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Place compound order response entity.
     *
     * @param orderAccounts the order accounts
     * @param email         the email
     * @return the response entity
     */
    @PostMapping(value = "/placeCompoundOrder/{email}")
    public ResponseEntity<Void> placeCompoundOrder(@RequestBody ArrayList<OrderAccount> orderAccounts,
                                                   @PathVariable(value = "email") String email){
        if(compoundOrderService.placeOrder(orderAccounts,email))
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * List orders response entity.
     *
     * @return the response entity
     */
    @GetMapping(value = "/listOrders")
    public ResponseEntity<ArrayList<Order>> listOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(OrderService.listOrders());
    }

    /**
     * Cancel order response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/cancelOrder/{id}")
    public ResponseEntity<Void> cancelOrder( @PathVariable(value = "id") int id){
        if(OrderService.cancel(id, false))
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    /**
     * Cancel order shipping response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/cancelOrderShipping/{id}")
    public ResponseEntity<Void> cancelOrderShipping( @PathVariable(value = "id") int id){
        if(OrderService.cancel(id, true))
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
