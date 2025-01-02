package com.culture_ticket.client.reservation_payment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    // 사용 예시
//  LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
   RESERVATION_GET_SUCCESS(HttpStatus.OK, "예약 내역이 조회되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}