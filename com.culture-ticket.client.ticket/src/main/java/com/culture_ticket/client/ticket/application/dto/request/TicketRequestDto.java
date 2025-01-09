package com.culture_ticket.client.ticket.application.dto.request;

import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class TicketRequestDto {

    private UUID performanceId;
    private UUID seatId;
    private Long ticketPrice;
    private UUID reservationId;
}
