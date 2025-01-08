package com.culture_ticket.client.performance.application.service;


import com.culture_ticket.client.performance.application.dto.pagination.RestPage;
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
import org.springframework.cache.annotation.Cacheable;
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
    public void createPerformance(String role, String username, PerformanceCreateRequestDto performanceCreateRequestDto) {
        validateRoleADMIN(role);
        checkDuplicateTitle(performanceCreateRequestDto.getTitle());

        Category category = categoryRepository.findByNameAndIsDeletedFalse(performanceCreateRequestDto.getCategory())
                .orElseThrow(() -> new CustomException(ErrorType.CATEGORY_NOT_FOUND));

        Performance performance = Performance.createPerformance(performanceCreateRequestDto, category);
        performance.setCreatedBy(username);
        performanceRepository.save(performance);
    }

    // 공연 단건 조회
    @Cacheable(cacheNames = "performanceCache", key = "#performanceId")
    @Transactional(readOnly = true)
    public PerformanceResponseDto getPerformance(UUID performanceId) {
        Performance performance = findPerformanceById(performanceId);
        return new PerformanceResponseDto(performance);
    }

    // 공연 전체 조회 & 검색
    @Cacheable(cacheNames = "performanceAllCache",
            key = "(#condition != null ? #condition : 'defaultCondition') + '-' + " +
                    "(#keyword != null && #keyword != '' ? #keyword : 'defaultKeyword') + '-' + " +
                    "(#pageable.pageNumber + '-' + #pageable.pageSize)"
    )
    @Transactional(readOnly = true)
    public RestPage<PerformanceResponseDto> getPerformances(String condition, String keyword, Pageable pageable) {
        return performanceRepository.findPerformanceWithConditions(condition, keyword, pageable);
    }

    // 공연 상태 수정
    @Transactional
    public void updatePerformanceStatus(
            String role, String username, UUID performanceId,
            UpdatePerformanceStatusRequestDto updatePerformanceStatusRequestDto
    ) {
        validateRoleADMIN(role);
        Performance performance = findPerformanceById(performanceId);
        performance.updatePerformanceStatus(updatePerformanceStatusRequestDto.getPerformanceStatus(), username);
    }

    // 공연 수정
    @Transactional
    public void updatePerformance(String role, String username, UUID performanceId, UpdatePerformanceRequestDto updatePerformanceRequestDto) {
        validateRoleADMIN(role);
        Performance performance = findPerformanceById(performanceId);
        Category category = findCategoryByName(updatePerformanceRequestDto.getCategory());
        performance.updatePerformance(updatePerformanceRequestDto, category, username);
    }

    // 공연 삭제
    @Transactional
    public void deletePerformance(String role, String username, UUID performanceId) {
        validateRoleADMIN(role);
        Performance performance = findPerformanceById(performanceId);
        performance.setDeletedBy(username);
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

    private void validateRoleADMIN(String role) {
        if (!role.equals("ADMIN")) {
            throw new CustomException(ErrorType.FORBIDDEN);
        }
    }
}
