package com.onm.ordersandnotificationsmanagement.orders.services;
import ch.qos.logback.core.joran.sanity.Pair;
import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.accounts.services.AccountService;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.models.OrderPlacementNotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.models.OrderShippmentNotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.services.NotificationTemplateService;
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

import java.security.KeyPair;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
   Map<Account,Order>ordersMade = new HashMap<>();

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
        compoundOrder.setOrderId(orderRepo.getNoOfOrders() + 1);
        orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() + 1);
        compoundOrder.setEmail(email);

        for(OrderAccount orderAccount: orderAccounts)
        {
            // return all account information
            Account account = AccountService.getAccountByEmail(orderAccount.getAccEmail());
            if (account == null) return false;

            SimpleOrder simpleOrder = new SimpleOrder();
            simpleOrder.setOrderId(orderRepo.getNoOfOrders() + 1);
            orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() + 1);
            simpleOrder.setEmail(account.getEmail());
            simpleOrder.setDate(LocalDateTime.now());


            // return all products' information
            for (Map.Entry<String, Integer> i : orderAccount.getProdSerialNum()) {
                Product p = productService.searchById(i.getKey());
                AbstractMap.SimpleEntry<Product, Integer> pair = new AbstractMap.SimpleEntry<>(p, i.getValue());
                addProduct(simpleOrder, pair);
                p.setAvailablePiecesNumber(p.getAvailablePiecesNumber() - i.getValue());
            }
            if(!deductOrder(simpleOrder, account)) return false;
            if(!shipOrder(simpleOrder, account)) return false;       // deduct order fees

            compoundOrder.getSimpleOrders().add(simpleOrder);

            compoundOrder.setOrderFees(compoundOrder.getOrderFees() + simpleOrder.getOrderFees());
            compoundOrder.setShippingFees(compoundOrder.getShippingFees() + simpleOrder.getShippingFees());
            // create notification
            NotificationTemplate NT = new OrderPlacementNotificationTemplate(account,
                    simpleOrder);
            NotificationTemplateService.addNotification(NT,account);
            // add order to account orders
            AccountService.addNewOrder(simpleOrder, account);
        }

        OrderService.add(compoundOrder);

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
            orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() - 1);
            for (Map.Entry<Product, Integer> product : simpleOrder.getProducts()) {
                Product p = productService.searchById(product.getKey().getSerialNumber());
                p.setAvailablePiecesNumber(p.getAvailablePiecesNumber() + product.getValue());
            }
        }
        OrderRepo.getOrders().remove(order);
        orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() - 1);
    }

    @Override
    public void cancelOrderShipment(Order order) {
        for(SimpleOrder simpleOrder: ((CompoundOrder)order).getSimpleOrders())
        {
            Account account = AccountService.getAccountByEmail(simpleOrder.getEmail());
            account.setBalance(account.getBalance() + simpleOrder.getShippingFees());
            simpleOrder.setShippingFees(0);
        }
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
        ordersMade.put(account,order);
        return true;
    }
    @Scheduled(cron = "0/10 * * ? * *")
    private void callShipNotification(){
        if(ordersMade.isEmpty())return;
        for(Map.Entry<Account,Order>entity : ordersMade.entrySet()){
            Duration duration = Duration.between(entity.getValue().getDate(), LocalDateTime.now());
            if (duration.toSeconds() >= ALLOWED_DURATION) {
                NotificationTemplate NT = new OrderShippmentNotificationTemplate(entity.getKey(),
                        entity.getValue());
                NotificationTemplateService.addNotification(NT,entity.getKey());
                ordersMade.remove(entity);
                if(ordersMade.isEmpty())return;
            }
        }
    }

    /**
     * Add product.
     *
     * @param order   the order
     * @param product the product
     */
    public void addProduct(SimpleOrder order, Map.Entry<Product, Integer> product){
        order.getProducts().add(product);
    }
}
