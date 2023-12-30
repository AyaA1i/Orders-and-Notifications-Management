package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.repos.NotificationTemplateRepo;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Queue;

/**
 * The type Notification template service.
 */
@NoArgsConstructor
@Component
public class NotificationTemplateService {
    /**
     * The constant ALLOWED_DURATION.
     */
    public static Integer ALLOWED_DURATION = 180;

    /**
     * Add notification.
     *
     * @param notificationTemplate the notification template
     * @param account              the account
     */
    public static void addNotification(NotificationTemplate notificationTemplate, Account account) {
        storeUsedTemp(notificationTemplate.getTemp());
        notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{x}",
                notificationTemplate.getPlaceholders()[0]));
        notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{y}",
                notificationTemplate.getPlaceholders()[1]));
        if (notificationTemplate.getPlaceholders().length == 3) {
            notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{z}",
                    notificationTemplate.getPlaceholders()[2]));
        }
        Notification notification = new Notification(notificationTemplate.getTemp(), LocalDateTime.now());
        Notifier notifier = new Notifier();
        notifier = new SMSNotifierDecorator(new EmailNotifierDecorator(notifier));
        notification.setTemp(notifier.sendNotification(notification));
        NotificationTemplateRepo.Notifications.add(notification);
        storeNotifiedAccounts(account);
    }

    /**
     * List all notifications queue.
     *
     * @return the queue
     */
    public static Queue<Notification> listAllNotifications() {
        return NotificationTemplateRepo.Notifications;
    }

    /**
     * Remove notification.
     */
    @Scheduled(cron = "0/10 * * ? * *")
    private void removeNotification() {
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

    /**
     * Get most notified string.
     *
     * @return the string
     */
    public static String getMostNotified(){
        String mostNotified = null;
        for(Map.Entry<String, Integer> Entry : NotificationTemplate.mostNotified.entrySet()){
            if((mostNotified == null )|| NotificationTemplate.mostNotified.get(mostNotified) < Entry.getValue()){
                mostNotified = Entry.getKey();
            }
        }
        return mostNotified;
    }

    /**
     * Get most used template string.
     *
     * @return the string
     */
    public static String getMostUsedTemplate(){
        String mostused = null;
        for(Map.Entry<String, Integer> Entry : NotificationTemplate.mostUsedTemp.entrySet()){
            if((mostused == null )|| NotificationTemplate.mostUsedTemp.get(mostused) < Entry.getValue()){
                mostused = Entry.getKey();
            }
        }
        return mostused;
    }
    private static void storeUsedTemp(String temp){
        if(NotificationTemplate.mostUsedTemp.get(temp)==null)
            NotificationTemplate.mostUsedTemp.put(temp,0);
        else
            NotificationTemplate.mostUsedTemp.put(temp,NotificationTemplate.mostUsedTemp.get(temp)+1);
    }

    private static void storeNotifiedAccounts(Account account){
        if(NotificationTemplate.mostNotified.get(account.getPhoneNumber())==null)
            NotificationTemplate.mostNotified.put(account.getPhoneNumber(),0);
        else
            NotificationTemplate.mostNotified.put(account.getPhoneNumber(),
                    NotificationTemplate.mostNotified.get(account.getPhoneNumber())+1);
        if(NotificationTemplate.mostNotified.get(account.getEmail())==null)
            NotificationTemplate.mostNotified.put(account.getEmail(),0);
        else
            NotificationTemplate.mostNotified.put(account.getEmail(),
                    NotificationTemplate.mostNotified.get(account.getEmail())+1);
    }

}
