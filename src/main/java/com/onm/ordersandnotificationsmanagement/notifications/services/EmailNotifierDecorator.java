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

    /**
     * send the notification via email
     * @param notification the notification
     * @param account      the account
     * @return String
     */
    @Override
    public String sendNotification(Notification notification, Account account) {
        storeNotifiedEmail(account);
        // sent notification via Email
        return super.sendNotification(notification, account) + " Sent From Email";
    }

    /**
     * store the notified email
     * @param account Account
     */
    private void storeNotifiedEmail(Account account) {
        // check if the email was notified before or not
        if(NotificationTemplate.mostNotified.get(account.getEmail())==null)
            // if not initialize with 0
            NotificationTemplate.mostNotified.put(account.getEmail(),0);
        else
            // else increment by 1
            NotificationTemplate.mostNotified.put(account.getEmail(),
                    NotificationTemplate.mostNotified.get(account.getEmail())+1);
    }

}