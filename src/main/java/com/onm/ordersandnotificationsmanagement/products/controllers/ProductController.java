package com.onm.ordersandnotificationsmanagement.products.controllers;

import com.onm.ordersandnotificationsmanagement.products.models.Category;
import com.onm.ordersandnotificationsmanagement.products.models.Product;
import com.onm.ordersandnotificationsmanagement.products.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

import static com.onm.ordersandnotificationsmanagement.utilities.ErrorHandler.getErrorHandlerMap;

/**
 * The type Product controller.
 */
@RequestMapping("/product")
@RestController
public class ProductController {
    /**
     * The Product service.
     */
    @Autowired
    ProductService productService;

    /**
     * Add product response entity.
     *
     * @param product the product
     * @return the response entity
     */
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product) {
        if (productService.addProduct(product))
            return ResponseEntity.ok("Product added successfully!");
        return ResponseEntity.badRequest().body("Unable to add product!");
    }

    /**
     * Remove product response entity.
     *
     * @param serialNumber the serial number
     * @return the response entity
     */
    @DeleteMapping("/remove/{serialNumber}")
    public ResponseEntity<String> removeProduct(@PathVariable("serialNumber") String serialNumber) {
        if (productService.removeProduct(serialNumber))
            return ResponseEntity.ok("Product removed successfully!");
        return ResponseEntity.badRequest().body("Unable to remove product!");
    }

    /**
     * List available products response entity.
     *
     * @return the response entity
     */
    @GetMapping("/list")
    public ResponseEntity<ArrayList<Product>> listAvailableProducts() {
        return ResponseEntity.ok(productService.listAvailableProducts());
    }

    /**
     * Count products response entity.
     *
     * @param category the category
     * @return the response entity
     */
    @GetMapping("/{categoryName}")      //count products in certain category
    public ResponseEntity<Integer> countProducts(@Valid @PathVariable(value = "categoryName") Category category) {
        return ResponseEntity.ok(productService.countProducts(category));
    }

    /**
     * Handle validation exceptions map.
     *
     * @param ex the ex
     * @return the map
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return getErrorHandlerMap(ex);
    }

}
