package com.example.jecoffee.product.exception;

public enum ProductExceptionCode {

    INVALID_PRODUCT_CATEGORY("[System] 존재하지 않는 상품 카테고리 입니다.");

    private String message;

    ProductExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
