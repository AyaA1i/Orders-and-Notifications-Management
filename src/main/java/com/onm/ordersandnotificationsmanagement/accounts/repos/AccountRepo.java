package com.onm.ordersandnotificationsmanagement.accounts.repos;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Setter
@Getter
public class AccountRepo {
    private final ArrayList<Account> accounts;

    public AccountRepo() {
        accounts = new ArrayList<>();
    }
    public void autofill()
    {
        accounts.add(new Account("1@gmail.com",100));
        accounts.add(new Account("2@gmail.com",100));
        accounts.add(new Account("3@gmail.com",100));
        accounts.add(new Account("4@gmail.com",100));
    }
    public Account getAccount(String email){
        for(Account account: accounts)
        {
            if(Objects.equals(account.getEmail(), email))
                return account;
        }
        return null;
    }
}
