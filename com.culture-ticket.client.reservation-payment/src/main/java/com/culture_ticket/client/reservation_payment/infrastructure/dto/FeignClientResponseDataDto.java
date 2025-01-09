package com.culture_ticket.client.reservation_payment.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FeignClientResponseDataDto<T> {
    private int status;
    private String message;
    private T data;

    @JsonCreator
    public FeignClientResponseDataDto(@JsonProperty("status") int status,
        @JsonProperty("message") String message,
        @JsonProperty("data") T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
