package com.onm.ordersandnotificationsmanagement.products.services;

import com.onm.ordersandnotificationsmanagement.products.models.Category;
import com.onm.ordersandnotificationsmanagement.products.models.Product;
import com.onm.ordersandnotificationsmanagement.products.repos.ProductRepo;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

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
     * @param serialNumber the serial number
     */
    public ArrayList<Product> removeProduct(String serialNumber) {
        for (Product product : ProductRepo.productList) {
            if (Objects.equals(product.getSerialNumber(), serialNumber)) {
                ProductRepo.productList.remove(product);
                break;
            }
        }
        return ProductRepo.productList;
    }

    /**
     * List all array list.
     *
     * @return the array list
     */
    public ArrayList<Product> listAvailableProducts() {
        ArrayList<Product> availableProducts = new ArrayList<>();
        for (Product product : ProductRepo.productList) {
            if (product.getAvailablePiecesNumber() > 0)
                availableProducts.add(product);
        }
        return availableProducts;
    }


    public Product searchById(String id){
        for(Product product: ProductRepo.productList){
            if(Objects.equals(product.getSerialNumber(), id))
                return product;
        }
        return null;
    }

    public Integer countProducts(Category category) {
        Integer count = 0;
        for (Product product : ProductRepo.productList) {
            if (product.getCategory() == category) {
                count++;
            }
        }
        return count;
    }

}
