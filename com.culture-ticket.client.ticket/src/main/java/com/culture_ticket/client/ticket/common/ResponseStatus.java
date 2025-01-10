package com.culture_ticket.client.ticket.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    // 사용 예시
    CREATE_TICKET_SUCCESS(HttpStatus.CREATED, "티켓 생성이 완료되었습니다."),
    GET_TICKET_SUCCESS(HttpStatus.OK, "티켓이 조회되었습니다."),
    DELETE_TICKET_SUCCESS(HttpStatus.OK, "티켓이 삭제되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
