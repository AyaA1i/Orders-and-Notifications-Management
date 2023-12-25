package com.onm.ordersandnotificationsmanagement.products.services;

import com.onm.ordersandnotificationsmanagement.products.models.Product;
import com.onm.ordersandnotificationsmanagement.products.repos.ProductRepo;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/**
 * The type Product service.
 */
@Service
public class ProductService {
    /**
     * Add.
     *
     * @param product the product
     * @return the array list
     */
    public ArrayList<Product> addProduct(Product product) {
        ProductRepo.productList.add(product);
        return ProductRepo.productList;
    }

    /**
     * Remove.
     *
     * @param product the product
     */
    public ArrayList<Product> removeProduct(Product product) {
        ProductRepo.productList.remove(product);
        return ProductRepo.productList;
    }

    /**
     * List all array list.
     *
     * @return the array list
     */
    public ArrayList<Product> listAllProducts() {
        return ProductRepo.productList;
    }
}
