package com.onm.ordersandnotificationsmanagement.notifications.models;

public class OrderShippmentNotificationTemplate extends NotificationTemplate{
    public OrderShippmentNotificationTemplate(String[] placeholders, String language) {
        ///Shipment with sara
        this.Placeholders = placeholders;
        // add order shipment fees in the template
        languages.put("English","Dear {x} , your shipment of the order with id : {y} is confirmed." +
                " thanks for using our store :) ");
        languages.put("German","Sehr geehrte {x}, Ihr Versand der Bestellung mit der ID: {y} ist bestätigt" +
                "Danke, dass Sie unseren Shop nutzen :)");
        this.temp = languages.get(language);

    }
}