package com.onm.ordersandnotificationsmanagement.orders.controllers;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.orders.models.OrderAccount;
import com.onm.ordersandnotificationsmanagement.orders.services.CompoundOrderService;
import com.onm.ordersandnotificationsmanagement.orders.services.OrderService;
import com.onm.ordersandnotificationsmanagement.orders.services.SimpleOrderService;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * The type Order controller.
 */
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
    public ResponseEntity<String> placeSimpleOrder(@RequestBody OrderAccount orderAccount){

        if(simpleOrderService.placeOrder(orderAccount) != null)
            return ResponseEntity.ok("Order Placed Successfully :)");
        return ResponseEntity.badRequest().body("Unable to place order!");
    }

    /**
     * Place compound order response entity.
     *
     * @param orderAccounts the order accounts
     * @param email         the email
     * @return the response entity
     */
    @PostMapping(value = "/placeCompoundOrder/{email}")
    public ResponseEntity<String> placeCompoundOrder(@RequestBody ArrayList<OrderAccount> orderAccounts,
                                                   @PathVariable(value = "email") String email){
        if(compoundOrderService.placeOrder(orderAccounts,email))
            return ResponseEntity.ok("Order Placed Successfully :)");
        return ResponseEntity.badRequest().body("Unable to place order!");
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
    public ResponseEntity<String> cancelOrder( @PathVariable(value = "id") int id){
        if(OrderService.cancel(id, false))
            return ResponseEntity.ok("Order Cancelled Successfully!");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't Cancel Order!");
    }

    /**
     * Cancel order shipping response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/cancelOrderShipping/{id}")
    public ResponseEntity<String> cancelOrderShipping( @PathVariable(value = "id") int id){
        if(OrderService.cancel(id, true))
            return ResponseEntity.ok("Shipping Cancelled Successfully!");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can't Cancel Shipping!");
    }
    @GetMapping(value = "/test")
    public ArrayList<Map .Entry<Account,Order>> test(){
        return SimpleOrderService.test();
    }



}
