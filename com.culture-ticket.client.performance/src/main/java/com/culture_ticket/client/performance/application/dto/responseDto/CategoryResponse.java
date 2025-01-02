package com.culture_ticket.client.performance.application.dto.responseDto;

import com.culture_ticket.client.performance.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private UUID id;
    private String name;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
