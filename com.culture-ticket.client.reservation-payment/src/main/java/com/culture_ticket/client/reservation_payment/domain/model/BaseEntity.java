package com.culture_ticket.client.reservation_payment.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
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

    protected void updatedBy(String email){
        this.updatedBy = email;
    }

    protected void softDeletedBy(String email) {
        this.isDeleted = true;
        this.deletedBy = email;
        this.deletedAt = LocalDateTime.now();
    }

    protected void restoreBy(String email) {
        this.isDeleted = false;
        this.deletedBy = null;
        this.deletedAt = null;
    }
}
