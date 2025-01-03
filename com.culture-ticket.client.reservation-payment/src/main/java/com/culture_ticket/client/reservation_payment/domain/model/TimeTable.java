package com.culture_ticket.client.reservation_payment.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class TimeTable {
    UUID perfomanceId;
    LocalDateTime date;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
