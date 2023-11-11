package com.example.jecoffee.product.controller.dto;

public class CreateProductRequest {
    private String productName;
    private String category;
    private long price;
    private String description;

    public CreateProductRequest(String productName, String category, long price, String description) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CreateProductRequest{" +
                "productName='" + productName + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}

