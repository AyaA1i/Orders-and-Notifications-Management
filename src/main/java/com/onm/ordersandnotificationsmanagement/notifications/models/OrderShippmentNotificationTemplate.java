package com.onm.ordersandnotificationsmanagement.notifications.models;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.notifications.services.Notifier;
import com.onm.ordersandnotificationsmanagement.notifications.services.SMSNotifierDecorator;

/**
 * The type Order shipment notification template.
 */
public class OrderShippmentNotificationTemplate extends NotificationTemplate{
    /**
     * Instantiates a new Order shipment notification template.
     *
     * @param account the account
     * @param order   the order
     */
    public OrderShippmentNotificationTemplate(Account account , Order order) {
        this.Placeholders = new String[]{account.getName() , String.valueOf(order.getOrderId()) , String.valueOf(order.getShippingFees())};
        languages.put("English","Dear {x} , your shipment of the order with id : {y} is confirmed and the shipment fees is {z}" +
                ". thanks for using our store :) ");
        languages.put("German","Sehr geehrte {x}, Ihr Versand der Bestellung mit der ID: {y} ist bestätigt. " +
                "wird bestätigt und die Versandkosten betragen {z}" +
                "Danke, dass Sie unseren Shop nutzen :)");
        this.temp = languages.get(account.getLanguage());
    }

}