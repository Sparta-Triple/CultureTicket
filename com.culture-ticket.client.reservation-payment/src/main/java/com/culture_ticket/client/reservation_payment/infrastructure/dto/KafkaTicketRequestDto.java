package com.culture_ticket.client.reservation_payment.infrastructure.dto;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KafkaTicketRequestDto {
    private UUID performanceId;
    private UUID seatId;
    private Long ticketPrice;
    private UUID reservationId;
    private String userId;
    private String username;
    private String role;

    @Builder
    private KafkaTicketRequestDto(UUID performanceId, UUID seatId, Long ticketPrice,
        UUID reservationId, String userId, String username, String role) {
        this.performanceId = performanceId;
        this.seatId = seatId;
        this.ticketPrice = ticketPrice;
        this.reservationId = reservationId;
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    public static KafkaTicketRequestDto of(UUID performanceId, UUID seatId, Long ticketPrice,
        UUID reservationId, String userId, String username, String role) {
        return builder()
            .performanceId(performanceId)
            .seatId(seatId)
            .ticketPrice(ticketPrice)
            .reservationId(reservationId)
            .userId(userId)
            .username(username)
            .role(role)
            .build();
    }
}
