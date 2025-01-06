package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.application.dto.responseDto.PerformanceResponseDto;
import com.culture_ticket.client.performance.domain.model.QPerformance;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PerformanceRepositoryCustomImpl implements PerformanceRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<PerformanceResponseDto> findPerformanceWithConditions(String condition, String keyword, Pageable pageable) {

        QPerformance qPerformance = QPerformance.performance;
        JPAQuery<PerformanceResponseDto> query = new JPAQuery<>(entityManager)
                .select(Projections.constructor(PerformanceResponseDto.class,
                        qPerformance.id,
                        qPerformance.title,
                        qPerformance.content,
                        qPerformance.venue,
                        qPerformance.casting,
                        qPerformance.startDate,
                        qPerformance.endDate,
                        qPerformance.performanceStatus,
                        qPerformance.category.name
                )).from(qPerformance)
                .where(qPerformance.isDeleted.eq(false));

        if (condition != null && keyword != null) {
            query.where(buildSearchCondition(condition, keyword));
        }

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = query.fetchCount();
        return new PageImpl<>(query.fetch(), pageable, total);
    }

    // 제목, 캐스팅, 카테고리 검색
    private BooleanExpression buildSearchCondition(String condition, String keyword) {
        if ("title".equalsIgnoreCase(condition)) {
            return QPerformance.performance.title.containsIgnoreCase(keyword);
        } else if ("content".equalsIgnoreCase(condition)) {
            return QPerformance.performance.content.containsIgnoreCase(keyword);
        } else if ("category".equalsIgnoreCase(condition)) {
            return QPerformance.performance.category.name.containsIgnoreCase(keyword);
        }

        return QPerformance.performance.title.contains(keyword)
                .or(QPerformance.performance.casting.contains(keyword))
                .or(QPerformance.performance.category.name.contains(keyword));
    }
}
