package com.culture_ticket.client.reservation_payment.infrastructure.dto;

import java.util.List;
import java.util.UUID;

public class SeatUpdateStatusDto {

    String username;
    String seatStatus;
    List<UUID> seatIds;
}
