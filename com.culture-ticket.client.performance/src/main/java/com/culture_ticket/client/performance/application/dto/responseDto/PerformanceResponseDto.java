package com.culture_ticket.client.performance.application.dto.responseDto;

import com.culture_ticket.client.performance.domain.model.Performance;
import com.culture_ticket.client.performance.domain.model.PerformanceStatusEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceResponseDto {
    private UUID id;
    private String title;
    private String content;
    private String venue;
    private String casting;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    private PerformanceStatusEnum performanceStatus;
    private String categoryName;

    public PerformanceResponseDto(Performance performance) {
        this.id = performance.getId();
        this.title = performance.getTitle();
        this.content = performance.getContent();
        this.venue = performance.getVenue();
        this.casting = performance.getCasting();
        this.startDate = performance.getStartDate();
        this.endDate = performance.getEndDate();
        this.performanceStatus = performance.getPerformanceStatus();
        this.categoryName = performance.getCategory().getName();
    }
}
