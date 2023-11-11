package com.example.jecoffee.order.controller;

import com.example.jecoffee.global.SuccessMessage;
import com.example.jecoffee.order.controller.dto.CreateOrderRequest;
import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.order.entity.Order;
import com.example.jecoffee.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<SuccessMessage> createOrder(@RequestBody CreateOrderRequest orderRequest) {
        Order order = orderService.createOrder(
                new Email(orderRequest.getEmail()),
                orderRequest.getAddress(),
                orderRequest.getPostcode(),
                orderRequest.getOrderItems()
        );
        return ResponseEntity.ok(
                new SuccessMessage("주문이 성공적으로 이루어졌습니다."
                        , HttpStatus.CREATED, order));
    }

    @GetMapping("/orders")
    public ResponseEntity<SuccessMessage> orderList() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(
                new SuccessMessage("주문 목록을 성공적으로 조회하였습니다."
                        , HttpStatus.CREATED, orders));
    }

    @PatchMapping("/orders/{orderId}")
    public ResponseEntity<SuccessMessage> changeOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        orderService.changeOrderStatus(UUID.fromString(orderId), status);

        return ResponseEntity.ok(
                new SuccessMessage("주문 상태를 성공적으로 수정하였습니다."
                        , HttpStatus.OK));
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<SuccessMessage> cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(UUID.fromString(orderId));

        return ResponseEntity.ok(
                new SuccessMessage("주문을 성공적으로 취소하였습니다."
                        , HttpStatus.OK));
    }
}
