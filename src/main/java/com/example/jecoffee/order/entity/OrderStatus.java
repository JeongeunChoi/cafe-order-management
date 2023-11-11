package com.example.jecoffee.order.entity;

import com.example.jecoffee.order.exception.OrderException;
import com.example.jecoffee.order.exception.OrderExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum OrderStatus {

    BEFORE_ACCEPTANCE("접수 전"),
    COOKING("조리 중"),
    DELIVERY_COMPLETED("배달 완료"),
    CANCELED("주문 취소");

    private final String name;

    private static final Logger logger = LoggerFactory.getLogger(OrderStatus.class);

    OrderStatus(String name) {
        this.name = name;
    }

    public static OrderStatus getValueByName(String name) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name.equals(name)) {
                return orderStatus;
            }
        }

        logger.error(OrderExceptionCode.INVALID_ORDER_STATUS.getMessage());
        throw new OrderException(OrderExceptionCode.INVALID_ORDER_STATUS);
    }
}
