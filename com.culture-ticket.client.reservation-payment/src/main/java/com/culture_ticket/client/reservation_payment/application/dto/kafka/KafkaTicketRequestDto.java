package com.culture_ticket.client.reservation_payment.application.dto.kafka;

import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KafkaTicketRequestDto {

    private UUID performanceId;
    private List<UUID> seatIds;
    private List<Long> ticketPrices;
    private UUID reservationId;
    private String userId;
    private String username;
    private String role;

    @Builder
    private KafkaTicketRequestDto(UUID performanceId, List<UUID> seatIds, List<Long> ticketPrices,
        UUID reservationId, String userId, String username, String role) {
        this.performanceId = performanceId;
        this.seatIds = seatIds;
        this.ticketPrices = ticketPrices;
        this.reservationId = reservationId;
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public static KafkaTicketRequestDto of(UUID performanceId, List<UUID> seatIds,
        List<Long> ticketPrices, UUID reservationId, String userId, String username, String role) {
        return builder()
            .performanceId(performanceId)
            .seatIds(seatIds)
            .ticketPrices(ticketPrices)
            .reservationId(reservationId)
            .userId(userId)
            .username(username)
            .role(role)
            .build();
    }
}
