package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.notifications.Notification;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.repos.NotificationTemplateRepo;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

@NoArgsConstructor
@Component
public class NotificationTemplateService {
    public static Integer ALLOWED_DURATION = 180;

    public static void addNotification(NotificationTemplate notificationTemplate) {
        notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{x}",
                notificationTemplate.getPlaceholders()[0]));
        notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{y}",
                notificationTemplate.getPlaceholders()[1]));
        if (notificationTemplate.getPlaceholders().length == 3) {
            notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{z}",
                    notificationTemplate.getPlaceholders()[2]));
        }
        Notification notification = new Notification(notificationTemplate.getTemp(), LocalDateTime.now());
        NotificationTemplateRepo.Notifications.add(notification);
    }

    public static Queue<Notification> listAllNotifications() {
        return NotificationTemplateRepo.Notifications;
    }

    @Scheduled(cron = "0/10 * * ? * *")
    public void removeNotification() {
        while (true) {
            if (NotificationTemplateRepo.Notifications.isEmpty()) break;
            Duration duration = Duration.between(NotificationTemplateRepo.Notifications.peek().getDate(), LocalDateTime.now());
            if (duration.toSeconds() > ALLOWED_DURATION) {
                NotificationTemplateRepo.Notifications.poll();
            } else {
                break;
            }
        }
    }

    public static String getMostNotified(){
        String mostNotified = null;
        for(Map.Entry<String, Integer> Entry : NotificationTemplate.mostNotified.entrySet()){
            if((mostNotified == null )|| NotificationTemplate.mostNotified.get(mostNotified) < Entry.getValue()){
                mostNotified = Entry.getKey();
            }
        }
        return mostNotified;
    }
    public static String getMostUsedTemplate(){
        String mostused = null;
        for(Map.Entry<String, Integer> Entry : NotificationTemplate.mostUsedTemp.entrySet()){
            if((mostused == null )|| NotificationTemplate.mostUsedTemp.get(mostused) < Entry.getValue()){
                mostused = Entry.getKey();
            }
        }
        return mostused;
    }


}
