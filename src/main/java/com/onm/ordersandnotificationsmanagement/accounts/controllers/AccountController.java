package com.onm.ordersandnotificationsmanagement.accounts.controllers;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.accounts.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * The type Account controller.
 */
@RestController
public class AccountController {
    /**
     * The constant currentAccount.
     */
    public static Account currentAccount;
    private static final AccountService accountService = new AccountService();

    /**
     * Sign up string.
     *
     * @param account the account
     * @return the string
     */
    @PostMapping ("/signUp")
    public String signUp(@RequestBody Account account){
        return accountService.signUp(account);
    }

    /**
     * Sign in string.
     *
     * @param email    the email
     * @param password the password
     * @return the string
     */
    @PostMapping ("/signIn/{email}/{password}")
    public String signIn(@PathVariable String email, @PathVariable String password){
        return accountService.signIn(email, password);
    }

    /**
     * Sign out.
     */
    @RequestMapping("/signOut")
    public void signOut(){
        accountService.signOut();
    }
}