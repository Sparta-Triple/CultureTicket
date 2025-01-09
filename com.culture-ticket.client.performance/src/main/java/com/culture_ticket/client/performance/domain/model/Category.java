package com.culture_ticket.client.performance.domain.model;

import com.culture_ticket.client.performance.application.dto.requestDto.CategoryRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_category")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Performance> performances = new ArrayList<>();

    public static Category createCategory(CategoryRequestDto request) {
        return Category.builder()
                .name(request.getName())
                .build();
    }

    public void setCreatedBy(String createdBy) {
        createdBy(createdBy);
    }

    public void setUpdateBy(String name, String updatedBy) {
        this.name = name;
        updatedBy(updatedBy);
    }

    public void setDeletedBy(String deletedBy) {
        softDeletedBy(deletedBy);
    }
}
