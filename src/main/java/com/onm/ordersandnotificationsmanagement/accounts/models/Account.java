package com.onm.ordersandnotificationsmanagement.accounts.models;

import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;

/**
 * The type Account.
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Account {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain at least one uppercase letter and one digit"
    )
    private String password;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 11, max = 11, message = "Phone number must be exactly 11 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only numeric digits")
    private String phoneNumber;

    @Pattern(regexp = "^(German|English)$", message = "Language must be either German or English")
    private String language;

    @Digits(integer = 1000000000, fraction = 10, message = "Balance must be a numeric value with up to 1000000000 integer digits and 10 fraction digits")
    private Double balance;

    @Pattern(regexp = "^(SMS|Email|Both)$", message = "Notification channel must be SMS ,Email or both")
    private String notificationChannel;

    private ArrayList<Order> orders = new ArrayList<>();
}