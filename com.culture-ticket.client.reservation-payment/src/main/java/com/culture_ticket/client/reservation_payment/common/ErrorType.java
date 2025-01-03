package com.culture_ticket.client.reservation_payment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //   사용예시
    // 결제
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND, "페이먼트가 존재하지 않습니다.");


  private final HttpStatus httpStatus;
  private final String message;
}