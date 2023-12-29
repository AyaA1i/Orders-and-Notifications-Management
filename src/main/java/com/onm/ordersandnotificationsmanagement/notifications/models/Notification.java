package com.onm.ordersandnotificationsmanagement.notifications.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The type Notification.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    /**
     * The Temp.
     */
    public String temp;
    /**
     * The Date.
     */
    public LocalDateTime date;
}
