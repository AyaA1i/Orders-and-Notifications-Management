package com.onm.ordersandnotificationsmanagement.orders;
import com.onm.ordersandnotificationsmanagement.accounts.Account;
import com.onm.ordersandnotificationsmanagement.products.Product;
import com.onm.ordersandnotificationsmanagement.accounts.AccountRepo;
import com.onm.ordersandnotificationsmanagement.products.ProductService;
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
    public void calcFees(Order order) {
        double fees = 0;
        for (Product product: ((SimpleOrder)order).getProducts())
        {
            fees += product.getPrice();
        }
        order.setOrderFees(fees);
        order.setShippingFees(20);      // default compound order shipping fees
    }


    public boolean placeOrder(ArrayList<OrderAccount> orderAccounts) {
        ////for testing
        AccountRepo accountRepo = new AccountRepo();
        accountRepo.autofill();
        productService.autoFill();
        /////for testing
        CompoundOrder compoundOrder = new CompoundOrder();


        for(OrderAccount orderAccount: orderAccounts)
        {
            // return all account information
            Account account = accountRepo.getAccount(orderAccount.getAccEmail());
            if (account == null) return false;

            SimpleOrder simpleOrder = new SimpleOrder();
            simpleOrder.setOrderId(orderAccount.getOrderId());

            // return all products' information
            for (Integer i : orderAccount.getProdIds()) {
                addProduct(simpleOrder, productService.getById(i));
            }
            if(!deductOrder(simpleOrder, account)) return false;

            compoundOrder.getSimpleOrders().add(simpleOrder);

            compoundOrder.setOrderId(compoundOrder.getOrderId() + simpleOrder.getOrderId());
            compoundOrder.setOrderFees(compoundOrder.getOrderFees() + simpleOrder.getOrderFees());
            compoundOrder.setShippingFees(compoundOrder.getShippingFees() + simpleOrder.getShippingFees());
            //TODO: call notification template
            //TODO: add order to user
        }

        OrderRepo.add(compoundOrder);

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

    public void addOrder(CompoundOrder order, SimpleOrder simpleOrder){
        order.getSimpleOrders().add(simpleOrder);
        calcFees(order);
    }
    public void addProduct(SimpleOrder order, Product product){
        order.getProducts().add(product);
    }
}
