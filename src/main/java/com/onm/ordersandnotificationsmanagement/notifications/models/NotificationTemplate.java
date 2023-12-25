package com.onm.ordersandnotificationsmanagement.notifications.models;
import com.onm.ordersandnotificationsmanagement.utilities.Languages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public abstract class NotificationTemplate {
    String [] Placeholders;
    String temp;
    Languages language;
}