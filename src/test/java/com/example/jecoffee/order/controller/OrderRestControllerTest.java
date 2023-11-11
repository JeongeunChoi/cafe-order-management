package com.example.jecoffee.order.controller;

import com.example.jecoffee.customer.entity.Email;
import com.example.jecoffee.order.controller.dto.CreateOrderRequest;
import com.example.jecoffee.order.entity.Order;
import com.example.jecoffee.order.entity.OrderItem;
import com.example.jecoffee.order.entity.OrderStatus;
import com.example.jecoffee.order.service.OrderService;
import com.example.jecoffee.product.entity.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(OrderRestController.class)
class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("주문 등록 성공")
    void Success_CreateOrder() throws Exception {
        // given
        Order order = createOrder();
        CreateOrderRequest orderRequest = new CreateOrderRequest(order.getEmail().getAddress(), order.getAddress(), order.getPostcode(), order.getOrderItems());
        when(orderService.createOrder(
                new Email(orderRequest.getEmail()),
                orderRequest.getAddress(),
                orderRequest.getPostcode(),
                orderRequest.getOrderItems())).thenReturn(order);

        // when, then
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(orderRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("주문이 성공적으로 이루어졌습니다."));
    }

    @Test
    @DisplayName("주문 목록 조회 성공")
    void Success_ListOrder() throws Exception {
        // given
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            orders.add(createOrder());
        }
        when(orderService.getAllOrders()).thenReturn(orders);

        // when, then
        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("주문 목록을 성공적으로 조회하였습니다."));
    }

    @Test
    @DisplayName("주문 상태 변경 성공")
    void Success_ChangeOrderStatus() throws Exception {
        // given
        String orderId = UUID.randomUUID().toString();
        String status = "조리 중";
        doNothing().when(orderService).changeOrderStatus(UUID.fromString(orderId), status);

        // when, then
        mockMvc.perform(patch("/api/v1/orders/{orderId}", orderId)
                        .param("status", status)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("주문 상태를 성공적으로 수정하였습니다."));
    }

    @Test
    @DisplayName("주문 취소 성공")
    void Success_CancelOrder() throws Exception {
        // given
        String orderId = UUID.randomUUID().toString();
        doNothing().when(orderService).cancelOrder(UUID.fromString(orderId));

        // when, then
        mockMvc.perform(delete("/api/v1/orders/{orderId}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("주문을 성공적으로 취소하였습니다."));
    }

    private Order createOrder() {
        UUID orderId = UUID.randomUUID();
        return new Order(
                orderId,
                new Email("test@case.com"),
                "부산 남구 용소로 45",
                "12345",
                createOrderItem(orderId),
                OrderStatus.BEFORE_ACCEPTANCE,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    private List<OrderItem> createOrderItem(UUID orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(orderId, UUID.randomUUID(), Category.COFFEE, 5000, 3));
        orderItems.add(new OrderItem(orderId, UUID.randomUUID(), Category.ADE, 5000, 3));
        orderItems.add(new OrderItem(orderId, UUID.randomUUID(), Category.TEA, 5000, 3));

        return orderItems;
    }
}