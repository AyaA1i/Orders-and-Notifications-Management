package com.onm.ordersandnotificationsmanagement.orders.controllers;

import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.orders.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.orders.services.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    @RequestMapping(value = "/order",method = RequestMethod.POST)
    public String addorder(@RequestBody SimpleOrder order){
        return OrderService.addorder(order);
    }
    @RequestMapping(value = "/order/{id}",method = RequestMethod.GET)
    public Order getorder(@PathVariable("id") int id){
        return OrderService.getOrder(id);
    }
}
