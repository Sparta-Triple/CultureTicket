package com.culture_ticket.client.ticket.application.dto;

import java.util.UUID;
import lombok.Getter;

@Getter
public class TicketRequestDto {

    private Long userId;
    private UUID performanceId;
    private UUID seatId;
    private Long ticketPrice;
}
