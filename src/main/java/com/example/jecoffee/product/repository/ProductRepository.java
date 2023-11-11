package com.example.jecoffee.product.repository;

import com.example.jecoffee.product.entity.Category;
import com.example.jecoffee.product.entity.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> findAll();

    Product insert(Product product);

    List<Product> findByName(String productName);

    List<Product> findByCategory(Category category);
}