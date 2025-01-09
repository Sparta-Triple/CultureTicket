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
    private Long userId;
    private UUID performanceId;
    private List<UUID> seatId;
    private Long ticketPrice;

    @Builder
    private TicketRequestDto(Long userId, UUID performanceId, List<UUID> seatId, Long ticketPrice) {
        this.userId = userId;
        this.performanceId = performanceId;
        this.seatId = seatId;
        this.ticketPrice = ticketPrice;
    }

    public static TicketRequestDto of(Long userId, UUID performanceId, List<UUID> seatId,
        Long ticketPrice) {
        return builder()
            .userId(userId)
            .performanceId(performanceId)
            .seatId(seatId)
            .ticketPrice(ticketPrice)
            .build();
    }
}
