package com.onm.ordersandnotificationsmanagement.orders.services;

import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.notifications.models.NotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.models.OrderShippmentNotificationTemplate;
import com.onm.ordersandnotificationsmanagement.notifications.services.NotificationsService;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.orders.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.products.services.ProductService;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


/**
 * The interface Order service.
 */
@Service
public interface OrderService {
    /**
     * The constant orderRepo.
     */
    OrderRepo orderRepo = new OrderRepo();
    /**
     * The constant productService.
     */
    ProductService productService = new ProductService();

    /**
     * The constant ALLOWED_DURATION.
     */
    Integer ALLOWED_DURATION = 120;

    /**
     * Calc order fees.
     *
     * @param order the order
     */
    void calcOrderFees(Order order);

    /**
     * Calc shipping fees.
     *
     * @param order the order
     */
    void calcShippingFees(Order order);

    /**
     * Ship order boolean.
     *
     * @param order   the order
     * @param account the account
     * @return the boolean
     */
    boolean shipOrder(Order order, Account account);

    /**
     * Deduct order boolean.
     *
     * @param order   the order
     * @param account the account
     * @return the boolean
     */
    boolean deductOrder(Order order, Account account);

    /**
     * Cancel order.
     *
     * @param order the order
     */
    void cancelOrder(Order order);

    /**
     * Cancel order shipment.
     *
     * @param order the order
     */
    void cancelOrderShipment(Order order);

    /**
     * Cancel boolean.
     *
     * @param orderId the order id
     * @param ship    the ship
     * @return the boolean
     */
    static boolean cancel(int orderId, boolean ship){
        Order order = OrderService.searchById(orderId);

        // order not found
        if (order == null)
            return false;

        Duration duration = Duration.between(order.getDate(),java.time.LocalDateTime.now());
        if(duration.toSeconds() > ALLOWED_DURATION){
            return false;
        }
        if(order instanceof SimpleOrder)
        {
            SimpleOrderService simpleOrderService = new SimpleOrderService();
            if(ship)
            {
               simpleOrderService.cancelOrderShipment(order);
            }
            else
                simpleOrderService.cancelOrder(order);
            return true;
        }
        CompoundOrderService compoundOrderService = new CompoundOrderService();
        if(ship)
        {
            compoundOrderService.cancelOrderShipment(order);
        }
        else
            compoundOrderService.cancelOrder(order);
        return true;
    }

    /**
     * List orders array list.
     *
     * @return the array list
     */
    static ArrayList<Order> listOrders() {
        return OrderRepo.getOrders();
    }

    /**
     * Add.
     *
     * @param order the order
     */
    public static void add(Order order){
        OrderRepo.getOrders().add(order);
    }

    /**
     * Search by id order.
     *
     * @param id the id
     * @return the order
     */
    public static Order searchById(int id){
        for(Order order: OrderRepo.getOrders()){
            if(order.getOrderId() == id)
                return order;
        }
        return null;
    }
    public default void checkDuration(Iterator<Map.Entry<Account, Order>> iterator){
        while (iterator.hasNext()) {
            Map.Entry<Account, Order> entity = iterator.next();
            Duration duration = Duration.between(entity.getValue().getDate(), LocalDateTime.now());

            if (duration.toSeconds() >= ALLOWED_DURATION && !entity.getValue().isCancelled()) {
                NotificationTemplate NT = new OrderShippmentNotificationTemplate(entity.getKey(), entity.getValue());
                NotificationsService.addNotification(NT, entity.getKey());
                iterator.remove(); // Use iterator's remove method
            }
        }
    }

}
