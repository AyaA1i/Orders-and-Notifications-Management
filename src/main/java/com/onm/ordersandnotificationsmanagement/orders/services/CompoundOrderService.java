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

import java.util.ArrayList;

@Setter
@Getter
@NoArgsConstructor
@Service
public class CompoundOrderService implements OrderService {
    private static final OrderRepo orderRepo = new OrderRepo();
    private static final ProductService productService = new ProductService();
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
        order.setShippingFees(20);      // default compound order shipping fees
    }

    //
    public boolean placeOrder(ArrayList<OrderAccount> orderAccounts,String email) {
        ////for testing
//        accountRepo.autofill();
//        productService.autoFill();
        /////for testing
        CompoundOrder compoundOrder = new CompoundOrder();
        compoundOrder.setEmail(email);

        for(OrderAccount orderAccount: orderAccounts)
        {
            // return all account information
            Account account = AccountService.accountRepo.getAccount(orderAccount.getAccEmail());
            if (account == null) return false;

            SimpleOrder simpleOrder = new SimpleOrder();
            simpleOrder.setOrderId(orderAccount.getOrderId());
            simpleOrder.setEmail(account.getEmail());

            // return all products' information
            for (String i : orderAccount.getProdSerialNum()) {
                addProduct(simpleOrder, productService.searchById(String.valueOf(i)));
            }
            if(!(deductOrder(simpleOrder, account) || shipOrder(simpleOrder, account))) return false; // deduct order fees

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
    public ArrayList<Order> cancelOrder(int orderId) {
        return null;
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

//    public void addOrder(CompoundOrder order, SimpleOrder simpleOrder){
//        order.getSimpleOrders().add(simpleOrder);
//        calcOrderFees(order);
//    }
    public void addProduct(SimpleOrder order, Product product){
        order.getProducts().add(product);
    }
}
