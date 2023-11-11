package com.example.jecoffee.order.service;

import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.order.entity.Order;
import com.example.jecoffee.order.entity.OrderItem;
import com.example.jecoffee.order.entity.OrderStatus;
import com.example.jecoffee.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;


    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems) {
        Order order = new Order(
                UUID.randomUUID(),
                email,
                address,
                postcode,
                orderItems,
                OrderStatus.BEFORE_ACCEPTANCE,
                LocalDateTime.now(),
                LocalDateTime.now());
        return orderRepository.insert(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void changeOrderStatus(UUID orderId, String orderStatus) {
        orderRepository.updateOrderStatus(orderId, OrderStatus.getValueByName(orderStatus));
    }

    @Override
    public void cancelOrder(UUID orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresentOrElse(
                o -> {
                    if (o.getOrderStatus() == OrderStatus.BEFORE_ACCEPTANCE) {
                        orderRepository.updateOrderStatus(orderId, OrderStatus.CANCELED);
                () -> {
                    logger.error(DataExceptionCode.DATA_NOT_EXIST.getMessage());
                    throw new DataException(DataExceptionCode.DATA_NOT_EXIST);
                }
        );
    }
}
