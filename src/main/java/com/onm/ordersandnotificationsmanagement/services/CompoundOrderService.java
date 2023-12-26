package com.onm.ordersandnotificationsmanagement.services;
import com.onm.ordersandnotificationsmanagement.models.Account;
import com.onm.ordersandnotificationsmanagement.models.CompoundOrder;
import com.onm.ordersandnotificationsmanagement.models.Order;
import com.onm.ordersandnotificationsmanagement.models.SimpleOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class CompoundOrderService extends OrderService{
    @Override
    public void calcFees(Order order) {
        double fees = 0;
        for (SimpleOrder simpleOrder: ((CompoundOrder)order).getSimpleOrders())
        {
            fees += simpleOrder.getOrderFees();
        }
        order.setOrderFees(fees);
    }

    @Override
    public boolean placeOrder(HashMap<Order, Account> orderAccountHashMap) {
        for (Map.Entry<Order, Account> entry : orderAccountHashMap.entrySet()) {
            Order order = entry.getKey();
            Account account = entry.getValue();

            double newBalance = account.getBalance() - (order.getOrderFees() + order.getShippingFees());
            if (newBalance >= 0) {
                account.setBalance(newBalance);
            } else {
                return false;
            }
        }

        //TODO: call notification template
        //TODO: add orders to database

        return true;
    }

    public void addOrder(Order order, SimpleOrder simpleOrder){
        ((CompoundOrder)order).getSimpleOrders().add(simpleOrder);
        calcFees(order);
    }
}
