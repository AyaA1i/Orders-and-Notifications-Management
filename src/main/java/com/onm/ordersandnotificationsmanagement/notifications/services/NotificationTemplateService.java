package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.notifications.Notification;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.repos.NotificationTemplateRepo;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
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
        Notification notification = new Notification(notificationTemplate.getTemp(), java.time.LocalDateTime.now());
        NotificationTemplateRepo.Notifications.add(notification);
    }

    public static Queue<Notification> listAllNotifications() {
        return NotificationTemplateRepo.Notifications;
    }

    @Scheduled(cron = "0/10 * * ? * *")
    public void removeNotification() {
        while (true) {
            if (NotificationTemplateRepo.Notifications.isEmpty()) break;
            Duration duration = Duration.between(NotificationTemplateRepo.Notifications.peek().getDate(), java.time.LocalDateTime.now());
            if (duration.toSeconds() > ALLOWED_DURATION) {
                NotificationTemplateRepo.Notifications.poll();
            } else {
                break;
            }
        }
    }

}
