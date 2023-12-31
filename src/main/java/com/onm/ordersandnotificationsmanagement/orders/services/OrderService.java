package com.onm.ordersandnotificationsmanagement.orders.services;
import com.onm.ordersandnotificationsmanagement.accounts.models.Account;
import com.onm.ordersandnotificationsmanagement.orders.repos.OrderRepo;
import com.onm.ordersandnotificationsmanagement.orders.models.Order;
import com.onm.ordersandnotificationsmanagement.orders.models.SimpleOrder;
import com.onm.ordersandnotificationsmanagement.products.services.ProductService;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.ArrayList;

/**
 * The interface Order service.
 */
@Service
public interface OrderService {
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
        // check if duration that has passed since the order was placed exceeds the allowed duration
        Duration duration = Duration.between(order.getDate(),java.time.LocalDateTime.now());
        if(duration.toSeconds() > ALLOWED_DURATION) {
            return false;
        }
        if(order instanceof SimpleOrder)
        {
            SimpleOrderService simpleOrderService = new SimpleOrderService();
            if(ship) // cancel shipment if the user chose to cancel shipment of the order
               simpleOrderService.cancelOrderShipment(order);
            else // cancel order if the user chose to cancel the order
                simpleOrderService.cancelOrder(order);
            return true;
        }
        else
        {
            CompoundOrderService compoundOrderService = new CompoundOrderService();
            if(ship) // cancel shipment if the user chose to cancel shipment of the order
                compoundOrderService.cancelOrderShipment(order);
            else // cancel order if the user chose to cancel the order
                compoundOrderService.cancelOrder(order);
            return true;
        }
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
    static void add(Order order){
        OrderRepo.getOrders().add(order);
    }

    /**
     * Search by id order.
     *
     * @param id the id
     * @return the order
     */
    static Order searchById(int id){
        for(Order order: OrderRepo.getOrders()){
            if(order.getOrderId() == id)
                return order;
        }
        return null;
    }

}
