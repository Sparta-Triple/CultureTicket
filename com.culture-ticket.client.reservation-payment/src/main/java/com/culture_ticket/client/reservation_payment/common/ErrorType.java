package com.culture_ticket.client.reservation_payment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //   사용예시
    // 결제
    SEAT_ALREADY_RESERVED(HttpStatus.CONFLICT, "해당 좌석은 이미 예약되었습니다."),
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "결제가 존재하지 않습니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "해당 결에 대한 권한이 없습니다."),
    NOT_FOUND_RESERVATION(HttpStatus.NOT_FOUND, "예약 내역이 존재하지 않습니다."),
    NOT_FOUND_SEATPAYMENT(HttpStatus.NOT_FOUND, "좌석결제 내역이 존재하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}