package com.onm.ordersandnotificationsmanagement.notifications.controllers;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.services.NotificationTemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;

@RestController
public class NotificationTemplateController {
    @RequestMapping(value = "/notifications",method = RequestMethod.GET)
    public Queue<String> getNotifications(){
        return NotificationTemplateService.listAllNotifications();
    }
}
