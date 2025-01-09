package com.culture_ticket.client.reservation_payment.infrastructure.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class TimeTableResponseDto {
    UUID perfomanceId;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
}
