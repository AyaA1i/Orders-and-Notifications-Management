package com.onm.ordersandnotificationsmanagement.repos;

import com.onm.ordersandnotificationsmanagement.models.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Setter
@Getter
public class AccountRepo {
    private final ArrayList<Account> accounts;

    public AccountRepo() {
        accounts = new ArrayList<>();
    }
    public void autofill()
    {
        accounts.add(new Account(1,100));
        accounts.add(new Account(2,100));
        accounts.add(new Account(3,100));
        accounts.add(new Account(4,100));
    }
    public Account getAccount(int id){
        for(Account account: accounts)
        {
            if(account.getId() == id)
                return account;
        }
        return null;
    }
}
