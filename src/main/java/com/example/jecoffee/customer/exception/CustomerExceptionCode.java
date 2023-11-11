package com.example.jecoffee.customer.exception;

public enum CustomerExceptionCode {

    INVALID_EMAIL_LENGTH("[System] 이메일 길이는 4~58자 사이로 입력해주세요."),
    INVALID_EMAIL_FORMAT("[System] 올바른 이메일 형식으로 입력해주세요."),
    DUPLICATED_EMAIL("[System] 사용 중인 이메일 입니다.");

    private String message;

    CustomerExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
