package com.culture_ticket.client.ticket.domain.repository;

import com.culture_ticket.client.ticket.domain.model.Ticket;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findAllByReservationId(UUID reservationId);
}
