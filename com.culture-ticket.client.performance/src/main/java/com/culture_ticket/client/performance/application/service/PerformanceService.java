package com.culture_ticket.client.performance.application.service;

import com.culture_ticket.client.performance.application.dto.requestDto.PerformanceCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdatePerformanceRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdatePerformanceStatusRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.PerformanceResponseDto;
import com.culture_ticket.client.performance.common.CustomException;
import com.culture_ticket.client.performance.common.ErrorType;
import com.culture_ticket.client.performance.domain.model.Category;
import com.culture_ticket.client.performance.domain.model.Performance;
import com.culture_ticket.client.performance.domain.repository.CategoryRepository;
import com.culture_ticket.client.performance.domain.repository.PerformanceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final CategoryRepository categoryRepository;

    public PerformanceService(PerformanceRepository performanceRepository, CategoryRepository categoryRepository) {
        this.performanceRepository = performanceRepository;
        this.categoryRepository = categoryRepository;
    }


    // 공연 생성
    @Transactional
    public void createPerformance(PerformanceCreateRequestDto performanceCreateRequestDto) {

        checkDuplicateTitle(performanceCreateRequestDto.getTitle());

        Category category = categoryRepository.findByNameAndIsDeletedFalse(performanceCreateRequestDto.getCategory())
                .orElseThrow(() -> new CustomException(ErrorType.CATEGORY_NOT_FOUND));

        Performance performance = Performance.createPerformance(performanceCreateRequestDto, category);
        performance.setCreatedBy("test@email.com");
        performanceRepository.save(performance);
    }

    // 공연 단일 조회
    @Transactional(readOnly = true)
    public PerformanceResponseDto getPerformance(UUID performanceId) {
        Performance performance = findPerformanceById(performanceId);
        return new PerformanceResponseDto(performance);
    }

    // 공연 전체 조회 & 검색 (title)
    @Transactional(readOnly = true)
    public Page<PerformanceResponseDto> getPerformances(String condition, String keyword, Pageable pageable) {
        return performanceRepository.findPerformanceWithConditions(condition, keyword, pageable);

    }

    // 공연 상태 수정
    @Transactional
    public void updatePerformanceStatus(UUID performanceId, UpdatePerformanceStatusRequestDto updatePerformanceStatusRequestDto) {
        Performance performance = findPerformanceById(performanceId);
        performance.updatePerformanceStatus(updatePerformanceStatusRequestDto.getPerformanceStatus(), "test@email.com");
    }

    // 공연 수정
    @Transactional
    public void updatePerformance(UUID performanceId, UpdatePerformanceRequestDto updatePerformanceRequestDto) {
        Performance performance = findPerformanceById(performanceId);
        Category category = findCategoryByName(updatePerformanceRequestDto.getCategory());
        performance.updatePerformance(updatePerformanceRequestDto, category, "test@email.com");
    }

    // 공연 삭제
    @Transactional
    public void deletePerformance(UUID performanceId) {
        Performance performance = findPerformanceById(performanceId);
        performance.setDeletedBy("deleteTest@email.com");
    }


    private void checkDuplicateTitle(String title) {
        if (performanceRepository.existsByTitle(title)) {
            throw new CustomException(ErrorType.PERFORMANCE_DUPLICATE);
        }
    }

    private Performance findPerformanceById(UUID performanceId) {

        Performance performance = performanceRepository.findPerformanceById(performanceId)
                .orElseThrow(() -> new CustomException(ErrorType.PERFORMANCE_NOT_FOUND));

        if (performance.getIsDeleted()) {
            throw new CustomException(ErrorType.PERFORMANCE_NOT_FOUND);
        }
        return performance;
    }

    private Category findCategoryByName(String categoryName) {
        return categoryRepository.findByNameAndIsDeletedFalse(categoryName)
                .orElseThrow(() -> new CustomException(ErrorType.CATEGORY_NOT_FOUND));
    }
}
