package com.example.jecoffee.product.service;

import com.example.jecoffee.product.entity.Category;
import com.example.jecoffee.product.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProductsByCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(String productName, Category category, long price, String description);

    List<Product> searchProductsByName(String name);
}
