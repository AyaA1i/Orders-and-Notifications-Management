package com.onm.ordersandnotificationsmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OrdersAndNotificationsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersAndNotificationsManagementApplication.class, args);
	}

}
