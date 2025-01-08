package com.culture_ticket.client.ticket.application.dto.response;

import com.culture_ticket.client.ticket.application.dto.request.TicketRequestDto;
import com.culture_ticket.client.ticket.domain.model.Ticket;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TicketResponseDto {

    private Long userId;
    private UUID performanceId;
    private UUID seatId;
    private Long ticketPrice;

    @Builder
    private TicketResponseDto(Long userId, UUID performanceId, UUID seatId, Long ticketPrice) {
        this.userId = userId;
        this.performanceId = performanceId;
        this.seatId = seatId;
        this.ticketPrice = ticketPrice;
    }

    public static TicketResponseDto from(Ticket ticket) {
        return builder()
            .userId(ticket.getUserId())
            .performanceId(ticket.getPerformanceId())
            .seatId(ticket.getSeatId())
            .ticketPrice(ticket.getTicketPrice())
            .build();
    }
}
