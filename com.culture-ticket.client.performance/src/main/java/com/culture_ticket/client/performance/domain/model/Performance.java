package com.culture_ticket.client.performance.domain.model;


import com.culture_ticket.client.performance.application.dto.requestDto.PerformanceCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdatePerformanceRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_performance")
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String venue;

    @Column(nullable = false)
    private String casting;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerformanceStatusEnum performanceStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    public static Performance createPerformance(PerformanceCreateRequestDto performanceCreateRequestDto, Category category) {
        return Performance.builder()
                .title(performanceCreateRequestDto.getTitle())
                .content(performanceCreateRequestDto.getContent())
                .venue(performanceCreateRequestDto.getVenue())
                .casting(performanceCreateRequestDto.getCasting())
                .startDate(performanceCreateRequestDto.getStartDate())
                .endDate(performanceCreateRequestDto.getEndDate())
                .performanceStatus(performanceCreateRequestDto.getPerformanceStatus())
                .category(category)
                .build();
    }

    public void setCreatedBy(String createdBy) {
        createdBy(createdBy);
    }

    public void setUpdateBy(String updatedBy) {
        updatedBy(updatedBy);
    }

    public void updatePerformanceStatus(PerformanceStatusEnum performanceStatus, String updatedBy) {
        this.performanceStatus = performanceStatus;
        setUpdateBy(updatedBy);
    }

    public void updatePerformance(UpdatePerformanceRequestDto updatePerformanceRequestDto, Category category, String updatedBy) {
        this.title = updatePerformanceRequestDto.getTitle();
        this.content = updatePerformanceRequestDto.getContent();
        this.venue = updatePerformanceRequestDto.getVenue();
        this.casting = updatePerformanceRequestDto.getCasting();
        this.startDate = updatePerformanceRequestDto.getStartDate();
        this.endDate = updatePerformanceRequestDto.getEndDate();
        this.category = category;
        setUpdateBy(updatedBy);
    }

    public void setDeletedBy(String deletedBy) {
        softDeletedBy(deletedBy);
    }
}
