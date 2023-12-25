package com.onm.ordersandnotificationsmanagement.orders.services;

// we need two services { simple , compound }
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.models.OrderShippmentNotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.services.NotificationTemplateService;
import com.onm.ordersandnotificationsmanagement.orders.models.*;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import org.springframework.stereotype.Service;

// we need two services { simple , compound }

@Service
public class OrderService {
    // deduct -> order + shipping

    public static String addorder(Order order) {
        OrderRepo.orders.add(order);
        String[] placehoders = new String[2];
        placehoders[0] = "Aya";
        placehoders[1] = String.valueOf(order.getId());
        NotificationTemplate n = new OrderShippmentNotificationTemplate(placehoders
                , "German");
        NotificationTemplateService.addNotification(n);
        return "Order added";
    }

    public static Order getOrder(int id) {
        for (Order n : OrderRepo.orders) {
            if (n.getId() == id) {
                return n;
            }
        }
        return null;
    }
}