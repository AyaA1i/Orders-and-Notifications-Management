package com.onm.ordersandnotificationsmanagement.notifications.models;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.orders.OrderAccount;
import com.onm.ordersandnotificationsmanagement.utilities.Channel;
import com.onm.ordersandnotificationsmanagement.utilities.EmailChannel;

public class OrderPlacementNotificationTemplate extends NotificationTemplate{
    public OrderPlacementNotificationTemplate(Account account , OrderAccount order) {
        this.Placeholders = new String[]{account.getName() , String.valueOf(order.getOrderId())};
        languages.put("English","Dear {x} , your booking of the order with id : {y} is confirmed. " +
                "thanks for using our store :) ");
        languages.put("German","Sehr geehrte {x}, Ihre Buchung der Bestellung mit der ID: {y} ist bestätigt." +
                "Danke, dass Sie unseren Shop nutzen :)");
        this.temp = languages.get(account.getLanguage());
        Channel ch = new EmailChannel();
        temp += ch.print();
    }

}
