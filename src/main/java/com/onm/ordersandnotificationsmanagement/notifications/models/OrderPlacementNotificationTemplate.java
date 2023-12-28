package com.onm.ordersandnotificationsmanagement.notifications.models;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.orders.OrderAccount;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.utilities.Channel;
import com.onm.ordersandnotificationsmanagement.utilities.EmailChannel;

public class OrderPlacementNotificationTemplate extends NotificationTemplate{
    public OrderPlacementNotificationTemplate(Account account , Order order) {
        this.Placeholders = new String[]{account.getName() , String.valueOf(order.getOrderId())};
        languages.put("English","Dear {x} , your booking of the order with id : {y} is confirmed. " +
                "thanks for using our store :) ");
        languages.put("German","Sehr geehrte {x}, Ihre Buchung der Bestellung mit der ID: {y} ist bestätigt." +
                "Danke, dass Sie unseren Shop nutzen :)");
        this.temp = languages.get(account.getLanguage());
        Channel ch = new EmailChannel();
        temp += ch.print();
        storeUsedTemp(temp);
        storeNotifiedAccounts(account);
    }
    private void storeUsedTemp(String temp){
        if(mostUsedTemp.get(temp)==null)
            mostUsedTemp.put(temp,0);
        else
            mostUsedTemp.put(temp,mostUsedTemp.get(temp)+1);
    }

    private void storeNotifiedAccounts(Account account){
        if(mostNotified.get(account.getEmail())==null)
            mostNotified.put(account.getEmail(),0);
        else
            mostNotified.put(account.getEmail(),mostNotified.get(account.getEmail())+1);
    }
}
