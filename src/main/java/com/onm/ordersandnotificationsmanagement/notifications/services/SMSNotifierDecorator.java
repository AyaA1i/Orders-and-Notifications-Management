package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;

/**
 * The type Sms channel.
 */
public class SMSNotifierDecorator extends NotifierDecorator {
    public SMSNotifierDecorator(Notifier notifier) {
        super(notifier);
    }
    @Override
    public String sendNotification(Notification notification, Account account) {
        storeNotifiedPhoneNumber(account);
        return super.sendNotification(notification, account) + "Sent From SMS -";
    }
    private void storeNotifiedPhoneNumber(Account account) {
        if(NotificationTemplate.mostNotified.get(account.getPhoneNumber())==null)
            NotificationTemplate.mostNotified.put(account.getPhoneNumber(),0);
        else
            NotificationTemplate.mostNotified.put(account.getPhoneNumber(),
                    NotificationTemplate.mostNotified.get(account.getPhoneNumber())+1);
    }
}