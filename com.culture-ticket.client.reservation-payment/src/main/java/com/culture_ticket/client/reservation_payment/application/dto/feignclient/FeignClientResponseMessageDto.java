package com.culture_ticket.client.reservation_payment.application.dto.feignclient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FeignClientResponseMessageDto {

    private int status;
    private String message;

    @JsonCreator
    public FeignClientResponseMessageDto(@JsonProperty("status") int status,
        @JsonProperty("message") String message) {
        this.status = status;
        this.message = message;
    }
}
