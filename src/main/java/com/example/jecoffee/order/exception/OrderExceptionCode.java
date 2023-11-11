package com.example.jecoffee.order.exception;

public enum OrderExceptionCode {

    INVALID_ORDER_STATUS("[System] 유효하지 않은 주문 상태입니다."),
    CANNOT_BE_CANCELED("[System] 조리 중이거나 배당 완료된 주문은 취소할 수 없습니다."),
    ALREADY_CANCELED("[System] 이미 취소 처리된 주문입니다.");

    private String message;

    OrderExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
