package com.example.jecoffee.order.entity;

import com.example.jecoffee.product.entity.Category;

import java.util.UUID;

public class OrderItem {
    private UUID orderId;
    private UUID productId;
    private Category category;
    private long price;
    private int quantity;

    public OrderItem(UUID orderId, UUID productId, Category category, long price, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public Category getCategory() {
        return category;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

