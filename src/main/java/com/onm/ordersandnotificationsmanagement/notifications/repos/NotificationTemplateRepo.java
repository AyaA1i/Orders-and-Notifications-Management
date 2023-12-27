package com.onm.ordersandnotificationsmanagement.notifications.repos;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class NotificationTemplateRepo {
    public static final Queue<String> Notifications = new ArrayDeque<>();

}
