package com.onm.ordersandnotificationsmanagement.products.repos;

import com.onm.ordersandnotificationsmanagement.products.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Setter
@Getter
@Repository
public class ProductRepo {
    ArrayList<Product> products;
    @Autowired
    public ProductRepo(ArrayList<Product> products)
    {
        this.products = products;
    }
    public ProductRepo() {
        this.products = new ArrayList<>();
    }

    public Product searchById(int id){
        for(Product product: products){
            if(product.getId() == id)
                return product;
        }
        return null;
    }
    public void addProduct(Product product){
        products.add(product);
    }
}
