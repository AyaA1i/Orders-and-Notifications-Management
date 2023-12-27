package com.onm.ordersandnotificationsmanagement.accounts.repos;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import java.util.ArrayList;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class AccountRepo {
    public static final ArrayList<Account> accountsList = new ArrayList<>();
    public void addAccount(Account account){
        accountsList.add(account);
    }
    public boolean searchAccount(Account account){
        if(!accountsList.contains(account)){
            addAccount(account);
            return false;
        }
        return true;
    }
    public Account searchAccount(String email, String password){
        for (Account account : accountsList) {
            if(account.getEmail().equals(email) && account.getPassword().equals(password)){
                return account;
            }
        }
        return null;
    }
    public Account getAccount(String email){
        for(Account account: accountsList)
        {
            if(Objects.equals(account.getEmail(), email))
                return account;
        }
        return null;
    }
}