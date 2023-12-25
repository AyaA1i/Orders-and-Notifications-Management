package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.repos.NotificationTemplateRepo;

import java.util.Queue;


public class NotificationTemplateService {
    public static void addNotification(NotificationTemplate notificationTemplate){
        notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{x}",
                notificationTemplate.getPlaceholders()[0]));
        notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{y}",
                notificationTemplate.getPlaceholders()[1]));
        NotificationTemplateRepo.Notifications.add(notificationTemplate);
    }
    public static StringBuilder listAllNotifications(){
        StringBuilder notifications = new StringBuilder();
        for(NotificationTemplate n : NotificationTemplateRepo.Notifications){
            notifications.append(n.getTemp());
            notifications.append('\n');
        }
        return notifications;
    }

}
