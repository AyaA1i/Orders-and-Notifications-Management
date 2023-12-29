package com.onm.ordersandnotificationsmanagement.accounts.repos;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import java.util.ArrayList;
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
}