package com.culture_ticket.client.ticket.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //   사용예시
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_FOUND_TICKET(HttpStatus.NOT_FOUND, "티켓이 존재하지 않습니다."),
    TICKET_CREATE_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "티켓 생성 중 에러가 발생하였습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
