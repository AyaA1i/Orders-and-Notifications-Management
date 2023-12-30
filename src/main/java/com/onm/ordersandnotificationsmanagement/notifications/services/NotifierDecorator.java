package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class NotifierDecorator extends Notifier{
    private Notifier notifier;
    @Override
    public String sendNotification(Notification notification, Account account) {
       return notifier.sendNotification(notification, account);
    }
}