package com.onm.ordersandnotificationsmanagement.notifications.repos;
import com.onm.ordersandnotificationsmanagement.notifications.Notification;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The type Notification template repo.
 */
@Service
public class NotificationTemplateRepo {
    /**
     * The constant Notifications.
     */
    public static final Queue<Notification> Notifications = new ArrayDeque<>();

}
