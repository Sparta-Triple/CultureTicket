package com.culture_ticket.client.ticket.common;

import lombok.Getter;

@Getter
public class ResponseDataDto<T> {
    private int status;
    private String message;
    private T data;

    public ResponseDataDto(ResponseStatus responseStatus, T data) {
        this.status = responseStatus.getHttpStatus().value();
        this.message = responseStatus.getMessage();
        this.data = data;
    }

    // 사용 예시
    // return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CARD_UPDATE_SUCCESS, responseDto));
}
