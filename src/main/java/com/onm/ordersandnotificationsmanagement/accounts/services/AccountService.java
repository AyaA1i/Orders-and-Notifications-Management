package com.onm.ordersandnotificationsmanagement.accounts.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.accounts.repos.AccountRepo;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * The type Account service.
 */
@Service
public class AccountService {
    /**
     * Sign up string.
     *
     * @param account the account
     * @return the string
     */
    public boolean createAccount(Account account){
        if(getAccountByEmail(account.getEmail()) == null){
            AccountRepo.accountsList.add(account);
            return true;
        }
        return false;
    }

    /**
     * Get account using email.
     *
     * @param email the email
     * @return the account
     */
    public static Account getAccountByEmail(String email){
        for(Account account: AccountRepo.accountsList)
        {
            if(Objects.equals(account.getEmail(), email))
                return account;
        }
        return null;
    }

    /**
     * Add new order.
     *
     * @param order the order
     */
    public static void addNewOrder(Order order, Account account){
        account.getOrders().add(order);
    }
}
