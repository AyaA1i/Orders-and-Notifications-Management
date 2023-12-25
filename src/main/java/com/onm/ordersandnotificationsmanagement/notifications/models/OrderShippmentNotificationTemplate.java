package com.onm.ordersandnotificationsmanagement.notifications.models;

import com.onm.ordersandnotificationsmanagement.utilities.Languages;

public class OrderShippmentNotificationTemplate extends NotificationTemplate{
    public OrderShippmentNotificationTemplate(String[] placeholders, Languages language) {
        this.Placeholders = placeholders;
        this.language = language;
        this.temp = "Dear {x} , your shipment of the {y} is confirmed. thanks for using our store :) ";
    }
}