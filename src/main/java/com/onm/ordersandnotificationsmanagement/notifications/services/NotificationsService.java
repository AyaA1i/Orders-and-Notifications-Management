package com.onm.ordersandnotificationsmanagement.notifications.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.repos.NotificationsRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * The type Notification template service.
 */
@Component
public class NotificationsService {
    /**
     * The constant ALLOWED_DURATION.
     */
    public static Integer ALLOWED_DURATION = 180;

    /**
     * this map used to store objects of the available channels to don't violate OCP
     */
    private static Map<String, Notifier> availableChannels;

    /**
     * Instantiates a new Channel.
     */
    NotificationsService() {
        availableChannels = new HashMap<>();
        addChannel("SMS", new SMSNotifierDecorator(new Notifier()));
        addChannel("Email", new EmailNotifierDecorator(new Notifier()));
        addChannel("Both", new EmailNotifierDecorator(new SMSNotifierDecorator(new Notifier())));
    }

    /**
     * Add channel.
     *
     * @param channelName the channel name
     * @param notifier    the notifier
     */
    public void addChannel(String channelName, Notifier notifier) {
        availableChannels.put(channelName, notifier);
    }

    /**
     * Add notification to the queue.
     *
     * @param notificationTemplate the notification template
     * @param account              the account
     */
    public static void addNotification(NotificationTemplate notificationTemplate, Account account) {
        //store the template used for the notification
        storeUsedTemp(notificationTemplate.getTemp());
        // replace placeholders with actual values
        notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{x}",
                notificationTemplate.getPlaceholders()[0]));
        notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{y}",
                notificationTemplate.getPlaceholders()[1]));
        if (notificationTemplate.getPlaceholders().length == 3) {
            notificationTemplate.setTemp(notificationTemplate.getTemp().replace("{z}",
                    notificationTemplate.getPlaceholders()[2]));
        }
        // send the notification via the wanted channel
        Notification notification = new Notification(notificationTemplate.getTemp(), LocalDateTime.now());
        Notifier notifier = availableChannels.get(account.getNotificationChannel());
        notification.setTemp(notifier.sendNotification(notification, account));
        // put the notification in the queue
        NotificationsRepo.Notifications.add(notification);
    }

    /**
     * List all notifications queue.
     *
     * @return the queue
     */
    public static Queue<Notification> listAllNotifications() {
        return NotificationsRepo.Notifications;
    }

    /**
     * Remove notification from the queue after the configured time.
     */
    @Scheduled(cron = "0/10 * * ? * *")
    private void removeNotification() {
        while (true) {
            if (NotificationsRepo.Notifications.isEmpty()) break;
            // if any notification stayed more than the Allowed duration at the queue
            // remove it
            Duration duration = Duration.between(NotificationsRepo.Notifications.peek().getDate(), LocalDateTime.now());
            if (duration.toSeconds() > ALLOWED_DURATION) {
                NotificationsRepo.Notifications.poll();
            } else {
                break;
            }
        }
    }

    /**
     * Get most notified phone number or email.
     *
     * @return the string
     */
    public static String getMostNotified(){
        if (NotificationTemplate.mostNotified.isEmpty())
            return "No Notifications Sent!";

        String mostused = null;
        //loop over all the phone numbers and emails notified before
        // get the max value
        for(Map.Entry<String, Integer> Entry : NotificationTemplate.mostNotified.entrySet()){
            if((mostused == null )|| NotificationTemplate.mostNotified.get(mostused) < Entry.getValue()){
                mostused = Entry.getKey();
            }
        }
        // if more than one email / phone number has the max number of notifications
        // all of them will be returned
        StringBuilder tmp = new StringBuilder(mostused);
        for(Map.Entry<String, Integer> Entry : NotificationTemplate.mostNotified.entrySet()){
            if (Objects.equals(NotificationTemplate.mostNotified.get(mostused), Entry.getValue())) {
                tmp.append(", ").append(Entry.getKey());
            }
        }

        return tmp.toString();
    }

    /**
     * Get most used template.
     *
     * @return the string
     */
    public static String getMostUsedTemplate(){
        if (NotificationTemplate.mostUsedTemp.isEmpty())
            return "No Templates Used!";
        //loop over all the templates used before
        // get the max value
        String mostused = null;
        for(Map.Entry<String, Integer> Entry : NotificationTemplate.mostUsedTemp.entrySet()){
            if((mostused == null )|| NotificationTemplate.mostUsedTemp.get(mostused) < Entry.getValue()){
                mostused = Entry.getKey();
            }
        }
        // if more than one template has the max number of usage
        // all of them will be returned
        StringBuilder tmp = new StringBuilder(mostused);
        for(Map.Entry<String, Integer> Entry : NotificationTemplate.mostUsedTemp.entrySet()){
            if (Objects.equals(NotificationTemplate.mostUsedTemp.get(mostused), Entry.getValue())) {
                tmp.append(", ").append(Entry.getKey());
            }
        }

        return tmp.toString();
    }

    /**
     * store the templates used in the map
     * @param temp
     */
    private static void storeUsedTemp(String temp){
        //if the template wasn't used before
        // initialize it with 0
        if(NotificationTemplate.mostUsedTemp.get(temp)==null)
            NotificationTemplate.mostUsedTemp.put(temp,0);
        else
            // else increment the number of usage by 1
            NotificationTemplate.mostUsedTemp.put(temp,NotificationTemplate.mostUsedTemp.get(temp)+1);
    }
}
