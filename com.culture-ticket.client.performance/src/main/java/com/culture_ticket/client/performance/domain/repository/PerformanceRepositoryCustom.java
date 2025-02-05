package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.application.dto.pagination.RestPage;
import com.culture_ticket.client.performance.application.dto.responseDto.PerformanceResponseDto;
import org.springframework.data.domain.Pageable;

public interface PerformanceRepositoryCustom {
    RestPage<PerformanceResponseDto> findPerformanceWithConditions(String condition, String keyword , Pageable pageable);
}
