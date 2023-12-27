package com.onm.ordersandnotificationsmanagement.products.services;

import com.onm.ordersandnotificationsmanagement.products.models.Product;
import com.onm.ordersandnotificationsmanagement.products.repos.ProductRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class ProductService {
    private static final ProductRepo productRepo = new ProductRepo();
    public Product getById(int id){
        return productRepo.searchById(id);
    }
    public void autoFill()
    {
        Product product1 = new Product(1,"Iphone",10);
        Product product2 = new Product(2,"mobile",20);
        Product product3 = new Product(3,"fries",30);
        Product product4 = new Product(4,"laptop",40);
        productRepo.addProduct(product1);
        productRepo.addProduct(product2);
        productRepo.addProduct(product3);
        productRepo.addProduct(product4);
    }
}
