package com.onm.ordersandnotificationsmanagement.accounts.repos;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import java.util.ArrayList;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Account repo.
 */
@Setter
@Getter
public class AccountRepo {
    /**
     * The constant accountsList.
     */
    public static final ArrayList<Account> accountsList = new ArrayList<>();

    /**
     * Add account.
     *
     * @param account the account
     */
    public void addAccount(Account account){
        accountsList.add(account);
    }

    /**
     * Search account boolean.
     *
     * @param account the account
     * @return the boolean
     */
    public boolean searchAccount(Account account){
        if(!accountsList.contains(account)){
            addAccount(account);
            return false;
        }
        return true;
    }

    /**
     * Search account account.
     *
     * @param email    the email
     * @param password the password
     * @return the account
     */
    public Account searchAccount(String email, String password){
        for (Account account : accountsList) {
            if(account.getEmail().equals(email) && account.getPassword().equals(password)){
                return account;
            }
        }
        return null;
    }

    /**
     * Get account account.
     *
     * @param email the email
     * @return the account
     */
    public Account getAccount(String email){
        for(Account account: accountsList)
        {
            if(Objects.equals(account.getEmail(), email))
                return account;
        }
        return null;
    }
}