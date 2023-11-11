package com.example.jecoffee.order.service;

import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.order.entity.Order;
import com.example.jecoffee.order.entity.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);

    List<Order> getAllOrders();

    void changeOrderStatus(UUID orderId, String status);

    void cancelOrder(UUID orderiD);
}
