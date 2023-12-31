package com.onm.ordersandnotificationsmanagement.accounts.controllers;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.accounts.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import static com.onm.ordersandnotificationsmanagement.utilities.ErrorHandler.getErrorHandlerMap;

/**
 * The type Account controller.
 */
@RestController
public class AccountController {
    /**
     * The constant currentAccount.
     */
    private static final AccountService accountService = new AccountService();

    /**
     * Sign up string.
     *
     * @param account the account
     * @return the string
     */
    @PostMapping ("/createAccount")
    public ResponseEntity<String> createAccount(@Valid @RequestBody Account account){
        if(accountService.createAccount(account)){
            return ResponseEntity.ok("Account created successfully :)\n");
        }
        return ResponseEntity.badRequest().body("This Account is Already Exist!\n");
    }

    /**
     * Handle validation exceptions map.
     *
     * @param ex the ex
     * @return the map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getErrorHandlerMap(ex);
    }
}