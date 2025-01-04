package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.application.dto.responseDto.PerformanceResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PerformanceRepositoryCustom {
    Page<PerformanceResponseDto> findPerformanceWithConditions(String condition, String keyword ,Pageable pageable);
}
