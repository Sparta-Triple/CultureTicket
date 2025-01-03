package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.application.dto.responseDto.CategoryResponse;
import com.culture_ticket.client.performance.domain.model.QCategory;
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
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<CategoryResponse> findCategoriesWithConditions(String keyword, Pageable pageable) {

        QCategory category = QCategory.category;

        JPAQuery<CategoryResponse> query = new JPAQuery<>(entityManager)
                .select(Projections.constructor(CategoryResponse.class,
                        category.id,
                        category.name))
                .from(category);

        if (keyword != null) {
            query.where(buildSearchCondition(keyword));
        }

        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = query.fetchCount();
        return new PageImpl<>(query.fetch(), pageable, total);
    }

    private BooleanExpression buildSearchCondition(String keyword) {
        return QCategory.category.name.containsIgnoreCase(keyword);
    }
}
