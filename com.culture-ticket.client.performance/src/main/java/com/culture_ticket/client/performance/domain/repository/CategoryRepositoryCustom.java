package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.application.dto.responseDto.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {
    Page<CategoryResponse> findCategoriesWithConditions(String keyword, Pageable pageable);
}
