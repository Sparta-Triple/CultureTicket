package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.domain.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, UUID>, PerformanceRepositoryCustom {
    boolean existsByTitle(String title);

    Optional<Performance> findPerformanceById(UUID performanceId);
}
