package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;

/**
 * The type Email channel.
 */
public class EmailNotifierDecorator extends NotifierDecorator {

    /**
     * Instantiates a new Email notifier decorator.
     *
     * @param notifier the notifier
     */
    public EmailNotifierDecorator(Notifier notifier) {
        super(notifier);
    }

    @Override
    public String sendNotification(Notification notification, Account account) {
        storeNotifiedEmail(account);
        return super.sendNotification(notification, account) + " Sent From Email";
    }

    private void storeNotifiedEmail(Account account) {
        if(NotificationTemplate.mostNotified.get(account.getEmail())==null)
            NotificationTemplate.mostNotified.put(account.getEmail(),0);
        else
            NotificationTemplate.mostNotified.put(account.getEmail(),
                    NotificationTemplate.mostNotified.get(account.getEmail())+1);
    }

}