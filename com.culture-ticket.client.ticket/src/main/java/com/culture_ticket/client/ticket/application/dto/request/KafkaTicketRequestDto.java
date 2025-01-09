package com.culture_ticket.client.ticket.application.dto.request;

import java.util.UUID;
import lombok.AccessLevel;
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

    @Override
    public String toString() {
        return "TicketRequestDto{" +
            "performanceId=" + performanceId +
            ", seatId=" + seatId +
            ", seatPrice=" + ticketPrice +
            ", reservationId=" + reservationId +
            ", userId='" + userId + '\'' +
            ", username='" + username + '\'' +
            ", role='" + role + '\'' +
            '}';
    }
}
