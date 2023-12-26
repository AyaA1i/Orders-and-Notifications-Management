package com.onm.ordersandnotificationsmanagement.services;
import com.onm.ordersandnotificationsmanagement.models.Account;
import com.onm.ordersandnotificationsmanagement.models.Order;
import com.onm.ordersandnotificationsmanagement.models.Product;
import com.onm.ordersandnotificationsmanagement.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.repos.AccountRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class SimpleOrderService extends OrderService{
    @Override
    public void calcFees(Order order) {
        double fees = 0;
        for (Product product: ((SimpleOrder)order).getProducts())
        {
            fees += product.getPrice();
        }
        order.setOrderFees(fees);
        order.setShippingFees(50); //default simple order shipping fees
    }
    @Override
    public boolean placeOrder(HashMap<Order, Account> orderAccountHashMap) {
        ////for testing
        AccountRepo accountRepo = new AccountRepo();
        accountRepo.autofill();
        /////for testing
        for (Map.Entry<Order, Account> entry : orderAccountHashMap.entrySet()) {
            Order order = entry.getKey();
            Account account = entry.getValue();

            account = accountRepo.getAccount(account.getId());
            if(account == null) return false;

            double newBalance = account.getBalance() - (order.getOrderFees() + order.getShippingFees());
            if (newBalance >= 0) {
                account.setBalance(newBalance);
                orderRepo.add(order);
            } else {
                return false;
            }
        }

        //TODO: call notification template
        //TODO: save in account DB
        return true;
    }
    public void addProduct(Order order, Product product){
        ((SimpleOrder)order).getProducts().add(product);
        calcFees(order);
    }
}
