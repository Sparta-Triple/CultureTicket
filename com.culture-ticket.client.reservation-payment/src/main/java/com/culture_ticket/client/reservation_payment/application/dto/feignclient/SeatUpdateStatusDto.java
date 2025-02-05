package com.culture_ticket.client.reservation_payment.application.dto.feignclient;

import java.util.List;
import java.util.UUID;

public class SeatUpdateStatusDto {

    String username;
    String seatStatus;
    List<UUID> seatIds;
}
