package com.culture_ticket.client.coupon.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    COUPON_STOCK_UNAVAILABLE(HttpStatus.BAD_REQUEST, "쿠폰 수량이 남아 있지 않습니다."),
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, "쿠폰이 존재하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "인가되지 않은 사용자입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}