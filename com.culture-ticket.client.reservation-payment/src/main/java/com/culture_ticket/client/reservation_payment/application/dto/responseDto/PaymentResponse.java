package com.culture_ticket.client.reservation_payment.application.dto.responseDto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    private UUID id;
    private Long totalPrice;

}
