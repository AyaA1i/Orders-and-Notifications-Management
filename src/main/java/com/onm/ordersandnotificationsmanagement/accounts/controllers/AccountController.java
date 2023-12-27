package com.onm.ordersandnotificationsmanagement.accounts.controllers;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.accounts.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class AccountController {
    public static Account currentAccount;
    private static final AccountService accountService = new AccountService();
    @PostMapping ("/signUp")
    public ArrayList<Account> signUp(@RequestBody Account account){
        return accountService.signUp(account);
    }
    @PostMapping ("/signIn/{email}/{password}")
    public String signIn(@PathVariable String email, @PathVariable String password){
        return accountService.signIn(email, password);
    }
    @RequestMapping("/signOut")
    public void signOut(){
        accountService.signOut();
    }
}