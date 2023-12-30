package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;

/**
 * The type Email channel.
 */
public class EmailNotifierDecorator extends NotifierDecorator {

    public EmailNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public String sendNotification(Notification notification) {
        return super.sendNotification(notification) + " Sent From Email";
    }

}