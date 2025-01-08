package com.culture_ticket.client.performance.application.dto.requestDto;

import com.culture_ticket.client.performance.domain.model.PerformanceStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PerformanceCreateRequestDto {
    private String category;
    private String title;
    private String description;
    private String venue;
    private String casting;
    private LocalDate startDate;
    private LocalDate endDate;
    private PerformanceStatusEnum performanceStatus;
}

