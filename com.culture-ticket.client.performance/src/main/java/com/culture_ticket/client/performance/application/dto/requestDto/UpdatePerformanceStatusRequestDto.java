package com.culture_ticket.client.performance.application.dto.requestDto;

import com.culture_ticket.client.performance.domain.model.PerformanceStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePerformanceStatusRequestDto {
    private PerformanceStatusEnum performanceStatus;
}
