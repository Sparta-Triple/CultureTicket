package com.culture_ticket.client.reservation_payment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //   사용예시
    //user
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이메일이 이미 사용 중입니다."),
    INVALID_EMAIL_OR_PASSWORD(HttpStatus.LOCKED, "이메일 또는 비밀번호가 잘못되었습니다."),
    NOT_FOUND_USER(HttpStatus.LOCKED, "존재하지 않는 회원입니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "이메일이 존재하지 않습니다."),
    NOT_AVAILABLE_PERMISSION(HttpStatus.LOCKED, "권한이 없습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "로그인 실패"),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "Refresh Token이 존재하지 않습니다. 다시 로그인 해주세요."),
    NOT_ACCESS_TOKEN(HttpStatus.NOT_FOUND, "Access Token이 존재하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "본인 정보만 접근할 수 있습니다."),

    //delivery_manager
    NOT_FOUND_DELIVERY_MANAGER(HttpStatus.NOT_FOUND, "배송 담당자가 존재하지 않습니다.");


  private final HttpStatus httpStatus;
  private final String message;
}