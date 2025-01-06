package com.culture_ticket.client.reservation_payment.domain.repository;

import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID>,
    ReservationRepositoryCustom {

    Page<Reservation> findAllByUserIdAndIsDeletedFalse(String userId, Pageable pageable);

    @Override
    Page<Reservation> findAllByUserIdAndDateRange(String userId, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    Page<Reservation> findAllByDateRange(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
}
