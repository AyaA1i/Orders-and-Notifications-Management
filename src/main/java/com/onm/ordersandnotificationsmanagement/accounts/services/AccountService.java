package com.onm.ordersandnotificationsmanagement.accounts.services;

import com.onm.ordersandnotificationsmanagement.accounts.controllers.AccountController;
import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.accounts.repos.AccountRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountService {
    private final AccountRepo accountRepo = new AccountRepo();
    public String signUp(Account account){
        if(!accountRepo.searchAccount(account)){
            AccountController.currentAccount = account;
            return "Account created Successfully :) \n";
        }
        return "This Account is Already Exist! \n";
    }
    public String signIn(String email, String password){
        Account tempAccount = accountRepo.searchAccount(email, password);
        if (tempAccount != null){
            AccountController.currentAccount = tempAccount;
            return "Signed in Successfully :) \n";
        }
        return "No Matching Information! \n";
    }
    public void signOut(){
        AccountController.currentAccount = null;
    }
}
