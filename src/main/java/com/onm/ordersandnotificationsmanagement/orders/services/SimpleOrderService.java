package com.onm.ordersandnotificationsmanagement.orders.services;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Setter
@Getter
@NoArgsConstructor
@Service
public class SimpleOrderService implements OrderService {
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
        order.setShippingFees(50.0); //default simple order shipping fees
    }

    public boolean placeOrder(OrderAccount orderAccount) {

        // return all account information
        Account account = AccountService.accountRepo.getAccount(orderAccount.getAccEmail());
        if (account == null) return false;

        SimpleOrder simpleOrder = new SimpleOrder();
        simpleOrder.setOrderId(orderRepo.getNoOfOrders() + 1);
        orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() + 1);
        simpleOrder.setEmail(account.getEmail());

        // return all products' information
        for (String i : orderAccount.getProdSerialNum()) {
            addProduct(simpleOrder, productService.searchById(i));
        }

        if (!deductOrder(simpleOrder, account)) return false;
        if(!shipOrder(simpleOrder, account)) return false;
        OrderService.add(simpleOrder);

        // create notification
        NotificationTemplate NT = new OrderPlacementNotificationTemplate(account,
                simpleOrder);
        NotificationTemplateService.addNotification(NT);
        // add order to the account orders
        account.addNewOrder(simpleOrder);
        simpleOrder.setDate(java.time.LocalDateTime.now());
        return true;
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
        NotificationTemplate NT = new OrderShippmentNotificationTemplate(account,
                order);
        NotificationTemplateService.addNotification(NT);
        return true;
    }

    @Override
    public boolean deductOrder(Order order, Account account) {
        calcOrderFees(order);
        double newBalance = account.getBalance() - (order.getOrderFees());
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
        Account account = AccountService.accountRepo.getAccount(order.getEmail());
        account.setBalance(account.getBalance() + order.getOrderFees() + order.getShippingFees());
        account.getOrders().remove(order);
        OrderRepo.getOrders().remove(order);
        orderRepo.setNoOfOrders(orderRepo.getNoOfOrders() - 1);
    }

    @Override
    public void cancelOrderShipment(Order order) {
        Account account = AccountService.accountRepo.getAccount(order.getEmail());
        account.setBalance(account.getBalance() + order.getShippingFees());
        order.setShippingFees(0);
    }

    public void addProduct(SimpleOrder order, Product product){
        order.getProducts().add(product);
    }
}
