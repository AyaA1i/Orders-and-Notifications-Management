package com.onm.ordersandnotificationsmanagement.notifications.controllers;
import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;
import com.onm.ordersandnotificationsmanagement.notifications.services.NotificationsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;

/**
 * The type Notification template controller.
 */
@RestController
public class NotificationsController {
    /**
     * Get notifications queue.
     *
     * @return the queue
     */
    //Note :
    // the notifications are removed from the queue after 3 minutes {bonus part}
    @RequestMapping(value = "/notifications",method = RequestMethod.GET)
    public Queue<Notification> getNotifications(){
        return NotificationsService.listAllNotifications();
    }

    /**
     * Get most notified phone number or Email.
     *
     * @return the string
     */
    @RequestMapping(value = "/mostNotified",method = RequestMethod.GET)
    public String getMostNotified(){
        return NotificationsService.getMostNotified();
    }

    /**
     * Get most used template.
     *
     * @return the string
     */
    @RequestMapping(value = "/mostUsedTemplate",method = RequestMethod.GET)
    public String getMostUsedTemp(){
        return NotificationsService.getMostUsedTemplate();
    }
}
