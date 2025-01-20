package com.culture_ticket.client.queue.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //   사용예시
    // Lock 관련
    LOCK_ACQUIRE_FAILED(HttpStatus.TOO_MANY_REQUESTS, "Lock을 획득하는데 실패하였습니다."),

    // Token 관련
    TOKEN_IS_FAILED(HttpStatus.BAD_REQUEST, "토큰 정보를 생성하는데 실패하였습니다"),
    ALREADY_TOKEN_IS_ACTIVE(HttpStatus.BAD_REQUEST, "이미 활성화 된 토큰입니다."),
    TOKEN_IS_NOT_YET(HttpStatus.BAD_REQUEST, "토큰 만료 대상이 아닙니다."),
    TOKEN_IS_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰이 존재하지 않습니다. 다시 토큰을 발급 후 시도해주세요."),
    TOKEN_IS_EXPIRED(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다, 재빌급 받아주세요."),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "토큰이 유효하지 않습니다, 토큰을 재발급 받으세요."),


    INVALID_TOKEN_PAYLOAD(HttpStatus.BAD_REQUEST, "USER ID 형식이 올바르지 않습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}