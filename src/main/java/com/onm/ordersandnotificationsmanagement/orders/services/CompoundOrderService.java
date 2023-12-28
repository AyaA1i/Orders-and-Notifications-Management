package com.onm.ordersandnotificationsmanagement.orders.services;
import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.accounts.services.AccountService;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.models.OrderPlacementNotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.models.OrderShippmentNotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.services.NotificationTemplateService;
import com.onm.ordersandnotificationsmanagement.orders.OrderAccount;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import com.onm.ordersandnotificationsmanagement.orders.models.CompoundOrder;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.orders.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.products.models.Product;
import com.onm.ordersandnotificationsmanagement.accounts.repos.AccountRepo;
import com.onm.ordersandnotificationsmanagement.products.services.ProductService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@Service
public class CompoundOrderService implements OrderService {
    @Override
    public void calcOrderFees(Order order) {
        double fees = 0;
        for (Product product: ((SimpleOrder)order).getProducts())
        {
            fees += product.getPrice();
        }
        order.setOrderFees(fees);
    }

    @Override
    public void calcShippingFees(Order order) {
        order.setShippingFees(20.0);      // default compound order shipping fees
    }

    public boolean placeOrder(ArrayList<OrderAccount> orderAccounts,String email) {

        CompoundOrder compoundOrder = new CompoundOrder();
        compoundOrder.setOrderId(orderRepo.getNoOfOrders() + 1);
        orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() + 1);
        compoundOrder.setEmail(email);

        for(OrderAccount orderAccount: orderAccounts)
        {
            // return all account information
            Account account = AccountService.accountRepo.getAccount(orderAccount.getAccEmail());
            if (account == null) return false;

            SimpleOrder simpleOrder = new SimpleOrder();
            simpleOrder.setOrderId(orderRepo.getNoOfOrders() + 1);
            orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() + 1);
            simpleOrder.setEmail(account.getEmail());
            simpleOrder.setDate(LocalDateTime.now());


            // return all products' information
            for (String i : orderAccount.getProdSerialNum()) {
                addProduct(simpleOrder, productService.searchById(String.valueOf(i)));
            }
            if(!deductOrder(simpleOrder, account)) return false;
            if(!shipOrder(simpleOrder, account)) return false;       // deduct order fees

            compoundOrder.getSimpleOrders().add(simpleOrder);

            compoundOrder.setOrderFees(compoundOrder.getOrderFees() + simpleOrder.getOrderFees());
            compoundOrder.setShippingFees(compoundOrder.getShippingFees() + simpleOrder.getShippingFees());
            // create notification
            NotificationTemplate NT = new OrderPlacementNotificationTemplate(account,
                    simpleOrder);
            NotificationTemplateService.addNotification(NT);
            // add order to account orders
            account.addNewOrder(simpleOrder);
        }

        OrderRepo.add(compoundOrder);

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
            Account account = AccountService.accountRepo.getAccount(simpleOrder.getEmail());
            account.setBalance(account.getBalance() + simpleOrder.getOrderFees() + simpleOrder.getShippingFees());
            account.getOrders().remove(simpleOrder);
            OrderRepo.getOrders().remove(simpleOrder);
            orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() - 1);
        }
        OrderRepo.getOrders().remove(order);
        orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() - 1);
    }

    @Override
    public void cancelOrderShipment(Order order) {
        for(SimpleOrder simpleOrder: ((CompoundOrder)order).getSimpleOrders())
        {
            Account account = AccountService.accountRepo.getAccount(simpleOrder.getEmail());
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
        NotificationTemplate NT = new OrderShippmentNotificationTemplate(account,
                order);
        NotificationTemplateService.addNotification(NT);
        return true;
    }

    public void addProduct(SimpleOrder order, Product product){
        order.getProducts().add(product);
    }
}
