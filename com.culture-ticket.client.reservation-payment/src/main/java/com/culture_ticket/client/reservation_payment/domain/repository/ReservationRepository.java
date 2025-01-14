package com.culture_ticket.client.reservation_payment.domain.repository;

import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.culture_ticket.client.reservation_payment.infrastructure.repository.ReservationRepositoryCustom;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID>,
    ReservationRepositoryCustom {

    Page<Reservation> findAllByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);

    Page<Reservation> findAllByUserIdAndDateRange(String userId, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    Page<Reservation> findAllByDateRange(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
}
