package com.culture_ticket.client.ticket.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //   사용예시
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
