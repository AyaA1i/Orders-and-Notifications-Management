package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;

/**
 * The interface Channel.
 */
public class Notifier {
    /**
     * Print string.
     *
     * @return the string
     */
    public String sendNotification(Notification notification) {
        return notification.temp;
    }
}