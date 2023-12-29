package com.onm.ordersandnotificationsmanagement.products.repos;

import com.onm.ordersandnotificationsmanagement.products.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.onm.ordersandnotificationsmanagement.products.models.Product;

import java.util.ArrayList;

/**
 * The type Product repo.
 */
public class ProductRepo {
    /**
     * The constant productList.
     */
    public static final ArrayList<Product> productList = new ArrayList<>();
}
