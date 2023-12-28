package com.onm.ordersandnotificationsmanagement.notifications.models;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public abstract class NotificationTemplate {
    String [] Placeholders;
    String temp;
    Map<String ,String>languages = new HashMap<>();
}