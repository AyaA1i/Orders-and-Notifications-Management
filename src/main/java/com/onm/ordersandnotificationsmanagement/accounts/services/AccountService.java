package com.onm.ordersandnotificationsmanagement.accounts.services;

import com.onm.ordersandnotificationsmanagement.accounts.controllers.AccountController;
import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.accounts.repos.AccountRepo;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * The type Account service.
 */
@Service
public class AccountService {
    /**
     * The constant accountRepo.
     */
    public static final AccountRepo accountRepo = new AccountRepo();

    /**
     * Sign up string.
     *
     * @param account the account
     * @return the string
     */
    public String signUp(Account account){
        if(!accountRepo.searchAccount(account)){
            AccountController.currentAccount = account;
            return "Account created Successfully :) \n";
        }
        return "This Account is Already Exist! \n";
    }

    /**
     * Sign in string.
     *
     * @param email    the email
     * @param password the password
     * @return the string
     */
    public String signIn(String email, String password){
        Account tempAccount = accountRepo.searchAccount(email, password);
        if (tempAccount != null){
            AccountController.currentAccount = tempAccount;
            return "Signed in Successfully :) \n";
        }
        return "No Matching Information! \n";
    }

    /**
     * Sign out.
     */
    public void signOut(){
        AccountController.currentAccount = null;
    }
}
