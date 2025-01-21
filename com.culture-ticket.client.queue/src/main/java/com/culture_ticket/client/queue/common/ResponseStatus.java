package com.culture_ticket.client.queue.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    // 사용 예시
    QUEUE_GET_SUCCESS(HttpStatus.OK, "대기열 활성여부를 조회하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}