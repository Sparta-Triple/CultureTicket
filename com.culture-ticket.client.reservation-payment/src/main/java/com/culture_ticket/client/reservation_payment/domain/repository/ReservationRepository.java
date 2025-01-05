package com.culture_ticket.client.reservation_payment.domain.repository;

import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.querydsl.core.types.Predicate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID>,
    QuerydslPredicateExecutor<Reservation> {

    Page<Reservation> findAllByUserId(String userId, Pageable pageable);

    Page<Reservation> findAllByUserId(String userId, Predicate predicate, Pageable pageable);

    Page<Reservation> findAll(Predicate predicate, Pageable pageable);
}
