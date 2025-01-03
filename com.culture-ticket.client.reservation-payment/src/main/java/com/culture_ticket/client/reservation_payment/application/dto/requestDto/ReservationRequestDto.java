package com.culture_ticket.client.reservation_payment.application.dto.requestDto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationRequestDto {

    private UUID paymentId;
    private Long userId;

}
