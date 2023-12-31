package com.onm.ordersandnotificationsmanagement.orders.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.accounts.services.AccountService;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.models.OrderPlacementNotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.services.NotificationsService;
import com.onm.ordersandnotificationsmanagement.orders.models.OrderAccount;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import com.onm.ordersandnotificationsmanagement.orders.models.CompoundOrder;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.orders.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.products.models.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.*;

/**
 * The type Compound order service.
 */
@Setter
@Getter
@NoArgsConstructor
@Service
@Component
public class CompoundOrderService implements OrderService {
    /**
     * The Orders made.
     */

    @Override
    public void calcOrderFees(Order order) {
        double fees = 0;
        for (Map.Entry<Product, Integer> product: ((SimpleOrder)order).getProducts())
        {
            fees += (product.getKey().getPrice() * product.getValue());
        }
        order.setOrderFees(fees);
    }

    @Override
    public void calcShippingFees(Order order) {
        order.setShippingFees(20.0);      // default compound order shipping fees
    }

    /**
     * Place order boolean.
     *
     * @param orderAccounts the order accounts
     * @param email         the email
     * @return the boolean
     */
    public boolean placeOrder(ArrayList<OrderAccount> orderAccounts,String email) {

        CompoundOrder compoundOrder = new CompoundOrder();
        compoundOrder.setOrderId(++OrderRepo.ordersID);
        Account account = AccountService.getAccountByEmail(email);
        if (account == null) return false;

        compoundOrder.setEmail(email);

        for(OrderAccount orderAccount: orderAccounts)
        {
            Account account1 = AccountService.getAccountByEmail(orderAccount.getAccEmail());

            SimpleOrderService simpleOrderService = new SimpleOrderService();
            SimpleOrder simpleOrder = simpleOrderService.placeOrder(orderAccount, false);
            if(simpleOrder == null) return false;

            if(!shipOrder(simpleOrder, account1)) return false;       // deduct order fees
            compoundOrder.getSimpleOrders().add(simpleOrder);

            compoundOrder.setOrderFees(compoundOrder.getOrderFees() + simpleOrder.getOrderFees());
            compoundOrder.setShippingFees(compoundOrder.getShippingFees() + simpleOrder.getShippingFees());
        }
        OrderService.add(compoundOrder);
        NotificationTemplate NT = new
                OrderPlacementNotificationTemplate(account, compoundOrder);
        NotificationsService.addNotification(NT,account);
        return true;
    }
    @Override
    public boolean deductOrder(Order order, Account account) {
        calcOrderFees(order);
        double newBalance = account.getBalance() - order.getOrderFees();
        if (newBalance >= 0) {
            account.setBalance(newBalance);
        } else {
            return false;
        }
        return true;
    }
    @Override
    public  void cancelOrder(Order order)
    {
        for(SimpleOrder simpleOrder: ((CompoundOrder)order).getSimpleOrders())
        {
            Account account = AccountService.getAccountByEmail(simpleOrder.getEmail());
            account.setBalance(account.getBalance() + simpleOrder.getOrderFees() + simpleOrder.getShippingFees());
            account.getOrders().remove(simpleOrder);
            OrderRepo.getOrders().remove(simpleOrder);
            for (Map.Entry<Product, Integer> product : simpleOrder.getProducts()) {
                Product p = productService.searchById(product.getKey().getSerialNumber());
                p.setAvailablePiecesNumber(p.getAvailablePiecesNumber() + product.getValue());
            }
            simpleOrder.setCancelled(true);
        }
        OrderRepo.getOrders().remove(order);
        order.setCancelled(true);
    }

    @Override
    public void cancelOrderShipment(Order order) {
        for(SimpleOrder simpleOrder: ((CompoundOrder)order).getSimpleOrders())
        {
            Account account = AccountService.getAccountByEmail(simpleOrder.getEmail());
            account.setBalance(account.getBalance() + simpleOrder.getShippingFees());
            simpleOrder.setCancelled(true);
            simpleOrder.setShippingFees(0);
        }
        order.setCancelled(true);
        order.setShippingFees(0);
    }

    @Override
    public boolean shipOrder(Order order, Account account) {
        calcShippingFees(order);
        double newBalance = account.getBalance() - order.getShippingFees();
        if (newBalance >= 0) {
            account.setBalance(newBalance);
        } else {
            return false;
        }
        return true;
    }

}
