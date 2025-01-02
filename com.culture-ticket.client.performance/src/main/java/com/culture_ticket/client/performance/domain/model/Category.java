package com.culture_ticket.client.performance.domain.model;

import com.culture_ticket.client.performance.application.dto.requestDto.CategoryRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_category")
public class Category extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "category_name", nullable = false)
    private String name;


    @Builder
    public static Category createCategory(CategoryRequest request) {

        return Category.builder()
                .name(request.getName())
                .build();
    }

    public void setCreatedBy(String createdBy){
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
