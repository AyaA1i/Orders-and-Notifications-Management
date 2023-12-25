package com.onm.ordersandnotificationsmanagement.notifications.models;

import com.onm.ordersandnotificationsmanagement.utilities.Languages;

public class OrderPlacementNotificationTemplate extends NotificationTemplate{
    public OrderPlacementNotificationTemplate(String[] placeholders, Languages language) {
        this.Placeholders = placeholders;
        this.language = language;
        this.temp = "Dear {x} , your booking of the {y} is confirmed. thanks for using our store :) ";
    }

}
