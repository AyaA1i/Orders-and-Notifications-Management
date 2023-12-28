package com.onm.ordersandnotificationsmanagement.notifications.controllers;
import com.onm.ordersandnotificationsmanagement.notifications.Notification;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.services.NotificationTemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;

@RestController
public class NotificationTemplateController {
    //Note :
    // the notifications are removed from the queue after 3 mins {bonus part}
    @RequestMapping(value = "/notifications",method = RequestMethod.GET)
    public Queue<Notification> getNotifications(){
        return NotificationTemplateService.listAllNotifications();
    }
}
