package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;

/**
 * The type Sms channel.
 */
public class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }
    @Override
    public String sendNotification(Notification notification) {
        return super.sendNotification(notification) + "Sent From SMS -";
    }
}