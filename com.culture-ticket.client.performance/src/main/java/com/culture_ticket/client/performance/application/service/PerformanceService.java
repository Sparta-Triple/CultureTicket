package com.culture_ticket.client.performance.application.service;


import com.culture_ticket.client.performance.application.dto.pagination.RestPage;
import com.culture_ticket.client.performance.application.dto.requestDto.PerformanceCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdatePerformanceRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdatePerformanceStatusRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.PerformanceResponseDto;
import com.culture_ticket.client.performance.common.CustomException;
import com.culture_ticket.client.performance.common.ErrorType;
import com.culture_ticket.client.performance.common.util.RedisKeyHelper;
import com.culture_ticket.client.performance.domain.model.Category;
import com.culture_ticket.client.performance.domain.model.Performance;
import com.culture_ticket.client.performance.domain.repository.CategoryRepository;
import com.culture_ticket.client.performance.domain.repository.PerformanceRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final CategoryRepository categoryRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    // 공연 생성
    @Transactional
    public UUID createPerformance(String username, String role, PerformanceCreateRequestDto performanceCreateRequestDto) {
        validateRoleADMIN(role);
        checkDuplicateTitle(performanceCreateRequestDto.getTitle());

        Category category = categoryRepository.findByNameAndIsDeletedFalse(performanceCreateRequestDto.getCategory())
                .orElseThrow(() -> new CustomException(ErrorType.CATEGORY_NOT_FOUND));

        Performance performance = Performance.createPerformance(performanceCreateRequestDto, category);
        performance.setCreatedBy(username);
        performanceRepository.save(performance);
        return performance.getId();
    }

    // 공연 단건 조회
    @Transactional(readOnly = true)
    public PerformanceResponseDto getPerformance(HttpServletRequest request, HttpServletResponse response, UUID performanceId) {
        String cacheKey = RedisKeyHelper.getPerformanceDetailsKey(performanceId);
        // 1. 쿠키 확인 후 24시간 이내에 조회한 적 없는 사용자일 경우 조회수 증가
        increaseViewCount(request, response, performanceId);
        // 2. 캐시에서 데이터 확인
        PerformanceResponseDto cachedPerformance = (PerformanceResponseDto) redisTemplate.opsForValue().get(cacheKey);
        if (cachedPerformance != null) return cachedPerformance;
        // 3. 캐시 데이터가 없으면 DB에서 조회
        Performance performance = findPerformanceById(performanceId);
        // 4. 조회 결과를 Redis 캐시에 저장
        PerformanceResponseDto performanceResponseDto = new PerformanceResponseDto(performance);
        redisTemplate.opsForValue().set(cacheKey, performanceResponseDto, Duration.ofHours(24)); // TTL 설정
        return new PerformanceResponseDto(performance);
    }

    private void increaseViewCount(HttpServletRequest request, HttpServletResponse response, UUID performanceId) {
        String rankKey = RedisKeyHelper.getViewRankKey();
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> oldCookie = cookies != null
                ? Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("View_Count"))
                    .findFirst()
                : Optional.empty();
        if (oldCookie.isPresent()) {
            Cookie cookie = oldCookie.get();
            if (!cookie.getValue().contains("[" + performanceId + "]")) {
                cookie.setValue(cookie.getValue() + "[" + performanceId + "]");
                redisTemplate.opsForZSet().incrementScore(rankKey, performanceId, 1);
            }
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
        } else {
            Cookie newCookie = new Cookie("View_Count", "[" + performanceId + "]");
            newCookie.setPath("/");
            redisTemplate.opsForZSet().incrementScore(rankKey, performanceId, 1);
            response.addCookie(newCookie);
        }
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

    @Transactional(readOnly = true)
    public List<PerformanceResponseDto> getPerformanceRank() {
        String rankKey = RedisKeyHelper.getViewRankKey();
        List<UUID> rankedPerformanceIds = redisTemplate.opsForZSet().reverseRangeWithScores(rankKey, 0, 5).stream().map(
                z -> UUID.fromString(z.getValue().toString())
        ).toList();
        List<PerformanceResponseDto> rankedPerformances = new ArrayList<>();
        for (UUID rankedPerformanceId : rankedPerformanceIds) {
            String cacheKey = RedisKeyHelper.getPerformanceDetailsKey(rankedPerformanceId);
            PerformanceResponseDto cachedPerformance = (PerformanceResponseDto) redisTemplate.opsForValue().get(cacheKey);
            rankedPerformances.add(cachedPerformance);
        }
        return rankedPerformances;
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
