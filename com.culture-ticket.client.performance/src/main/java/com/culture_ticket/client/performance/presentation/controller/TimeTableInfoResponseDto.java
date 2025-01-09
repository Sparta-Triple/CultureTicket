package com.culture_ticket.client.performance.presentation.controller;

import com.culture_ticket.client.performance.domain.model.TimeTable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class TimeTableInfoResponseDto {
    UUID perfomanceId;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;

    public TimeTableInfoResponseDto(TimeTable timeTable) {
        this.perfomanceId = timeTable.getPerformance().getId();
        this.date = timeTable.getDate();
        this.startTime = timeTable.getStartTime();
        this.endTime = timeTable.getEndTime();
    }

}
