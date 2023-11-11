package com.example.jecoffee.order.repository;

import com.example.jecoffee.order.entity.Order;
import com.example.jecoffee.order.entity.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order insert(Order order);

    List<Order> findAll();

    Optional<Order> findById(UUID orderId);

    void updateOrderStatus(UUID orderId, OrderStatus orderStatus);
}
