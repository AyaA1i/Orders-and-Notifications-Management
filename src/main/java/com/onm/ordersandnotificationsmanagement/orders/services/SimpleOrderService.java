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
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.orders.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.products.models.Product;
import com.onm.ordersandnotificationsmanagement.products.repos.ProductRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;


/**
 * The type Simple order service.
 */
@Setter
@Getter
@NoArgsConstructor
@Service
public class SimpleOrderService implements OrderService {
    /**
     * The Orders made.
     */
    ArrayList<Map.Entry<Account,Order>>ordersMade = new ArrayList<>();
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
        order.setShippingFees(50.0); //default simple order shipping fees
    }

    /**
     * Place order boolean.
     *
     * @param orderAccount the order account
     * @return the boolean
     */
    public SimpleOrder placeOrder(OrderAccount orderAccount, boolean flag) {

        // return all account information
        Account account = AccountService.getAccountByEmail(orderAccount.getAccEmail());
        if (account == null) return null;

        SimpleOrder simpleOrder = new SimpleOrder();
        simpleOrder.setOrderId(orderRepo.getNoOfOrders() + 1);
        orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() + 1);
        simpleOrder.setEmail(account.getEmail());
        simpleOrder.setDate(java.time.LocalDateTime.now());

        // return all products' information
        for (Map.Entry<String, Integer> i : orderAccount.getProdSerialNum()) {
            Product p = productService.searchById(i.getKey());
            System.out.println(i.getKey());
            Map.Entry<Product, Integer> m = Map.entry(p, i.getValue());
            addProduct(simpleOrder, m);
            p.setAvailablePiecesNumber(p.getAvailablePiecesNumber() - i.getValue());
        }
        // add order to the account orders
        AccountService.addNewOrder(simpleOrder, account);

        if (!deductOrder(simpleOrder, account)) return null;

        // call from controller
        if(flag)
        {
            if(!shipOrder(simpleOrder, account)) return null;
            OrderService.add(simpleOrder);
        }

        // create notification
        NotificationTemplate NT = new OrderPlacementNotificationTemplate(account,
                simpleOrder);
        NotificationTemplateService.addNotification(NT,account);

        return simpleOrder;
    }

    @Override
    public boolean shipOrder(Order order, Account account) {
        calcShippingFees(order);
        double newBalance = account.getBalance() - (order.getShippingFees());
        if (newBalance >= 0) {
            account.setBalance(newBalance);
        } else {
            return false;
        }
        ordersMade.add(Map.entry(account,order));
        return true;
    }
    @Scheduled(cron = "0/10 * * ? * *")
    private void callShipNotification(){
        if(ordersMade.isEmpty())return;
        for(Map.Entry<Account,Order>entity : ordersMade){
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
    public void cancelOrder(Order order)
    {
        Account account = AccountService.getAccountByEmail(order.getEmail());
        account.setBalance(account.getBalance() + order.getOrderFees() + order.getShippingFees());

        for (Map.Entry<Product, Integer> product : ((SimpleOrder)order).getProducts()) {
            Product p = productService.searchById(product.getKey().getSerialNumber());
            p.setAvailablePiecesNumber(p.getAvailablePiecesNumber() + product.getValue());
        }
        account.getOrders().remove(order);
        OrderRepo.getOrders().remove(order);
        orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() - 1);
    }

    @Override
    public void cancelOrderShipment(Order order) {
        Account account = AccountService.getAccountByEmail(order.getEmail());
        account.setBalance(account.getBalance() + order.getShippingFees());
        order.setShippingFees(0);
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
