package com.culture_ticket.client.reservation_payment.infrastructure.dto;

import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketRequestDto {

    private UUID performanceId;
    private UUID seatId;
    private Long ticketPrice;
    private UUID reservationId;

    @Builder
    private TicketRequestDto(UUID performanceId, UUID seatId, Long ticketPrice,
        UUID reservationId) {
        this.performanceId = performanceId;
        this.seatId = seatId;
        this.ticketPrice = ticketPrice;
        this.reservationId = reservationId;
    }

    public static TicketRequestDto of(UUID performanceId, UUID seatId, Long ticketPrice,
        UUID reservationId) {
        return builder()
            .performanceId(performanceId)
            .seatId(seatId)
            .ticketPrice(ticketPrice)
            .reservationId(reservationId)
            .build();
    }
}
