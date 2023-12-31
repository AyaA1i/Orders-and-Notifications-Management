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
     * used to calc the fees of the compound order
     * @param order the order
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

    /**
     * default compound order shipping fees
     * @param order the order
     */
    @Override
    public void calcShippingFees(Order order) {
        order.setShippingFees(20.0);
    }

    /**
     * Place order
     *
     * @param orderAccounts the order accounts
     * @param email         the email
     * @return the boolean
     */
    public boolean placeOrder(ArrayList<OrderAccount> orderAccounts,String email) {

        CompoundOrder compoundOrder = new CompoundOrder();
        // set the id for the order
        compoundOrder.setOrderId(++OrderRepo.ordersID);
        // check if the email exist
        Account account = AccountService.getAccountByEmail(email);
        if (account == null) return false;
        //set email of the order
        compoundOrder.setEmail(email);
        // loop over all the simple orders inside the compound order
        for(OrderAccount orderAccount: orderAccounts)
        {
            Account account1 = AccountService.getAccountByEmail(orderAccount.getAccEmail());

            SimpleOrderService simpleOrderService = new SimpleOrderService();
            // place the simple order
            SimpleOrder simpleOrder = simpleOrderService.placeOrder(orderAccount, false);
            if(simpleOrder == null) return false;
            // deduct order shipping fees
            if(!shipOrder(simpleOrder, account1)) return false;
            compoundOrder.getSimpleOrders().add(simpleOrder);
            // recalculate the fees of the compound
            compoundOrder.setOrderFees(compoundOrder.getOrderFees() + simpleOrder.getOrderFees());
            compoundOrder.setShippingFees(compoundOrder.getShippingFees() + simpleOrder.getShippingFees());
        }
        // add the order to the repo
        OrderService.add(compoundOrder);
        // call the notification sender to notify the placement of the compound order
        NotificationTemplate NT = new
                OrderPlacementNotificationTemplate(account, compoundOrder);
        NotificationsService.addNotification(NT,account);
        return true;
    }

    /**
     * used to deduct the order fees from the customer
     * @param order   the order
     * @param account the account
     * @return boolean
     */
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

    /**
     * used to cancel the order
     * @param order the order
     */
    @Override
    public  void cancelOrder(Order order)
    {
        // loop over all the simple orders inside the compound order
        for(SimpleOrder simpleOrder: ((CompoundOrder)order).getSimpleOrders())
        {
            // get the account that placed the order
            Account account = AccountService.getAccountByEmail(simpleOrder.getEmail());
            // return the fees for him
            account.setBalance(account.getBalance() + simpleOrder.getOrderFees() + simpleOrder.getShippingFees());
            // remove that order from the user account
            account.getOrders().remove(simpleOrder);
            // remove the order from the repo
            OrderRepo.getOrders().remove(simpleOrder);
            // loop over the products inside the order to reset it's availability
            for (Map.Entry<Product, Integer> product : simpleOrder.getProducts()) {
                Product p = productService.searchById(product.getKey().getSerialNumber());
                p.setAvailablePiecesNumber(p.getAvailablePiecesNumber() + product.getValue());
            }
            // set the simple order inside the compound order to cancel
            simpleOrder.setCancelled(true);
        }
        // remove the compound order from the repo
        OrderRepo.getOrders().remove(order);
        // set the compound order to cancel
        order.setCancelled(true);
    }

    /**
     * cancel the order shipment
     * @param order the order
     */
    @Override
    public void cancelOrderShipment(Order order) {
        // loop over the simple orders inside the compound order
        for(SimpleOrder simpleOrder: ((CompoundOrder)order).getSimpleOrders())
        {
            // return the user the fees of the order
            Account account = AccountService.getAccountByEmail(simpleOrder.getEmail());
            account.setBalance(account.getBalance() + simpleOrder.getShippingFees());
            // set the simple order inside the compound to cancel
            simpleOrder.setCancelled(true);
            // set the order shipment fees to 0
            simpleOrder.setShippingFees(0);
        }
        // set the compound order to cancel
        order.setCancelled(true);
        // set the shipping for thr compound order
        order.setShippingFees(0);
    }

    /**
     * ship the order
     * @param order   the order
     * @param account the account
     * @return boolean
     */
    @Override
    public boolean shipOrder(Order order, Account account) {
        // calc the order fees
        calcShippingFees(order);
        // calc the uer balance
        double newBalance = account.getBalance() - order.getShippingFees();
        if (newBalance >= 0) {
            account.setBalance(newBalance);
        } else {
            return false;
        }
        return true;
    }

}
