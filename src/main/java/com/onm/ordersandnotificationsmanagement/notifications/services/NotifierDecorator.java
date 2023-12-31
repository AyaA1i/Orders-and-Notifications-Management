package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;
import lombok.AllArgsConstructor;

/**
 * The type Notifier decorator.
 */
@AllArgsConstructor
public abstract class NotifierDecorator extends Notifier{
    private Notifier notifier;

    /**
     * send notification
     * @param notification the notification
     * @param account      the account
     * @return
     */
    @Override
    public String sendNotification(Notification notification, Account account) {
       return notifier.sendNotification(notification, account);
    }
}
