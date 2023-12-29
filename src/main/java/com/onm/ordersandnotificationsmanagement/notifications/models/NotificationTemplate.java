package com.onm.ordersandnotificationsmanagement.notifications.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Notification template.
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class NotificationTemplate {
    /**
     * The Placeholders.
     */
    String[] Placeholders;
    /**
     * The Temp.
     */
    String temp;
    /**
     * The Languages.
     */
    Map<String, String> languages = new HashMap<>();
    /**
     * The constant mostNotified.
     */
    public static Map<String, Integer> mostNotified = new HashMap<>();
    /**
     * The constant mostUsedTemp.
     */
    public static Map<String, Integer> mostUsedTemp = new HashMap<>();

}