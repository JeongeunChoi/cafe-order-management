package com.example.jecoffee.product.entity;

import com.example.jecoffee.product.exception.ProductException;
import com.example.jecoffee.product.exception.ProductExceptionCode;
import com.example.jecoffee.order.repository.OrderJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Category {
    COFFEE("커피"),
    LATTE("라떼"),
    SMOOTHIE("스무디"),
    JUICE("주스"),
    ADE("에이드"),
    TEA("차");

    private String name;

    private static final Logger logger = LoggerFactory.getLogger(OrderJdbcRepository.class);

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Category getValueByName(String name) {
        for (Category category : Category.values()) {
            if (category.name.equals(name)) {
                return category;
            }
        }

        logger.error(ProductExceptionCode.INVALID_PRODUCT_CATEGORY.getMessage());
        throw new ProductException(ProductExceptionCode.INVALID_PRODUCT_CATEGORY);
    }
}
