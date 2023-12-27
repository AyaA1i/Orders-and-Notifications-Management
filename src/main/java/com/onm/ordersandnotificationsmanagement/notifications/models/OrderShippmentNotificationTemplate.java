package com.onm.ordersandnotificationsmanagement.notifications.models;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.orders.OrderAccount;
import com.onm.ordersandnotificationsmanagement.utilities.Channel;
import com.onm.ordersandnotificationsmanagement.utilities.SMSChannel;

public class OrderShippmentNotificationTemplate extends NotificationTemplate{
    public OrderShippmentNotificationTemplate(Account account , OrderAccount order) {
        this.Placeholders = new String[]{account.getName() , String.valueOf(order.getOrderId())};
        languages.put("English","Dear {x} , your shipment of the order with id : {y} is confirmed and the shipment fees is {z}" +
                ". thanks for using our store :) ");
        languages.put("German","Sehr geehrte {x}, Ihr Versand der Bestellung mit der ID: {y} ist bestätigt. " +
                "wird bestätigt und die Versandkosten betragen {z}" +
                "Danke, dass Sie unseren Shop nutzen :)");
        this.temp = languages.get(account.getLanguage());
        temp += '\n';
        Channel ch = new SMSChannel();
        temp += ch.print();

    }
}