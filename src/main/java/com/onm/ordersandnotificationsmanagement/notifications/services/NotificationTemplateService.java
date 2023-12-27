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
        if(notificationTemplate.getPlaceholders().length==3){
            notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{z}",
                    notificationTemplate.getPlaceholders()[2]));
        }
        NotificationTemplateRepo.Notifications.add(notificationTemplate.getTemp());
    }
    public static Queue<String> listAllNotifications(){
        return NotificationTemplateRepo.Notifications;
    }

}
