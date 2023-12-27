package com.onm.ordersandnotificationsmanagement.orders.services;
import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.models.OrderPlacementNotificationTemplate;
import com.onm.ordersandnotificationsmanagement.orders.OrderAccount;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.orders.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.products.models.Product;
import com.onm.ordersandnotificationsmanagement.accounts.repos.AccountRepo;
import com.onm.ordersandnotificationsmanagement.products.services.ProductService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Setter
@Getter
@NoArgsConstructor
@Service
public class SimpleOrderService implements OrderService {
    private static final OrderRepo orderRepo = new OrderRepo();
    private static final ProductService productService = new ProductService();
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
    public boolean placeOrder(OrderAccount orderAccount) {
        ////for testing
        AccountRepo accountRepo = new AccountRepo();
        accountRepo.autofill();
        productService.autoFill();
        /////for testing

        // return all account information
        Account account = accountRepo.getAccount(orderAccount.getAccEmail());
        if (account == null) return false;

        SimpleOrder simpleOrder = new SimpleOrder();
        simpleOrder.setOrderId(orderAccount.getOrderId());

        // return all products' information
        for (Integer i : orderAccount.getProdIds()) {
            addProduct(simpleOrder, productService.getById(i));
        }

        if (!deductOrder(simpleOrder, account)) return false;
        OrderRepo.add(simpleOrder);

        //TODO: call notification template
        NotificationTemplate NT = new OrderPlacementNotificationTemplate(account,
                orderAccount);

        return true;
    }

    @Override
    public boolean deductOrder(Order order, Account account) {
        calcFees(order);
        double newBalance = account.getBalance() - (order.getOrderFees() + order.getShippingFees());
        if (newBalance >= 0) {
            account.setBalance(newBalance);
        } else {
            return false;
        }
        return true;
    }

    public void addProduct(SimpleOrder order, Product product){
        order.getProducts().add(product);
    }
}
