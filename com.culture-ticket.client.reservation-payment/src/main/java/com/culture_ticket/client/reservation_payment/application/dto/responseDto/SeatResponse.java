package com.culture_ticket.client.reservation_payment.application.dto.responseDto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponse {
    private UUID seatId;
    private String seatType;
    private Long price;
    private boolean available; // 유효 여부
}