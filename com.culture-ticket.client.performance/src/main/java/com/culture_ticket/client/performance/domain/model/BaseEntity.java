package com.culture_ticket.client.performance.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private String createdBy;

    private String updatedBy;

    private String deletedBy;

    private Boolean isDeleted = false;

    protected void createdBy(String username) {
        this.createdBy = username;
        this.updatedBy = username;
    }

    protected void updatedBy(String username){
        this.updatedBy = username;
    }

    protected void softDeletedBy(String username) {
        this.isDeleted = true;
        this.deletedBy = username;
        this.deletedAt = LocalDateTime.now();
    }

    protected void restoreBy(String username){
        this.isDeleted = false;
        this.deletedBy = null;
        this.deletedAt = null;
    }
}