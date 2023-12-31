package com.onm.ordersandnotificationsmanagement.notifications.repos;
import com.onm.ordersandnotificationsmanagement.notifications.models.Notification;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The type Notification template repo.
 */
@Service
public class NotificationsRepo {
    /**
     * The constant Notifications Queue.
     */
    public static final Queue<Notification> Notifications = new ArrayDeque<>();

}
