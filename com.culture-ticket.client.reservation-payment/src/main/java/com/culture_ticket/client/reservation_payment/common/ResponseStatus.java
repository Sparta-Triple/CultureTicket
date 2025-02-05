package com.culture_ticket.client.reservation_payment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    // 사용 예시
    PAYMENT_SUCCESS(HttpStatus.CREATED, "좌석 결제에 성공했습니다."),
    GET_PAYMENT_SUCCESS(HttpStatus.OK, "결제 내역 조회에 성공했습니다."),
    GET_RESERVATION_SUCCESS(HttpStatus.OK, "예약 내역이 조회되었습니다."),
    DELETE_RESERVATION_SUCCESS(HttpStatus.OK, "예약이 취소되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}