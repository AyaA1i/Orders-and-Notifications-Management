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
    public boolean addProduct(Product product) {
        if (searchById(product.getSerialNumber()) == null) {
            ProductRepo.productList.add(product);
            return true;
        }
        return false;
    }

    /**
     * Remove.
     *
     * @param serialNumber the serial number
     * @return the boolean
     */
    public boolean removeProduct(String serialNumber) {
        if (ProductRepo.productList.isEmpty())
            return true;
        int sizeBefore = ProductRepo.productList.size();
        for (Product product : ProductRepo.productList) {
            if (Objects.equals(product.getSerialNumber(), serialNumber)) {
                ProductRepo.productList.remove(product);
                break;
            }
        }
        return sizeBefore - 1 == ProductRepo.productList.size();
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


    /**
     * Search by id product.
     *
     * @param id the id
     * @return the product
     */
    public Product searchById(String id){
        for(Product product: ProductRepo.productList){
            if(Objects.equals(product.getSerialNumber(), id))
                return product;
        }
        return null;
    }

    /**
     * Count products integer.
     *
     * @param category the category
     * @return the integer
     */
    public Integer countProducts(Category category) {
        int count = 0;
        for (Product product : ProductRepo.productList) {
            if (product.getCategory() == category) {
                count += product.getAvailablePiecesNumber();
            }
        }
        return count;
    }

}
