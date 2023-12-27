package com.onm.ordersandnotificationsmanagement.products;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@AllArgsConstructor
public class Product {
    private int id;
    private String name;
    private double price;

}
