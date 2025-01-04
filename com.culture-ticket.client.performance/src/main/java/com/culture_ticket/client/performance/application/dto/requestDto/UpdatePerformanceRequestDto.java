package com.culture_ticket.client.performance.application.dto.requestDto;

import com.culture_ticket.client.performance.domain.model.PerformanceStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UpdatePerformanceRequestDto {
    private String category;
    private String title;
    private String content;
    private String venue;
    private String casting;
    private PerformanceStatusEnum PerformanceStatus;
    private LocalDate startDate;
    private LocalDate endDate;
}
