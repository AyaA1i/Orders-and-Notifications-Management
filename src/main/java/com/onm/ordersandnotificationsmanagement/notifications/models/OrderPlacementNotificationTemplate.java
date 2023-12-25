package com.onm.ordersandnotificationsmanagement.notifications.models;

public class OrderPlacementNotificationTemplate extends NotificationTemplate{
    public OrderPlacementNotificationTemplate(String[] placeholders, String language) {
        this.Placeholders = placeholders;
        languages.put("English","Dear {x} , your booking of the order with id : {y} is confirmed. " +
                "thanks for using our store :) ");
        languages.put("German","Sehr geehrte {x}, Ihre Buchung der Bestellung mit der ID: {y} ist bestätigt." +
                "Danke, dass Sie unseren Shop nutzen :)");
        this.temp = languages.get(language);
    }

}
