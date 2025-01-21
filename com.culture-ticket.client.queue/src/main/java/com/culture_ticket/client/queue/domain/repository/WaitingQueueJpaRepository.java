package com.culture_ticket.client.queue.domain.repository;

import com.culture_ticket.client.queue.domain.medel.WaitingQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingQueueJpaRepository extends JpaRepository<WaitingQueueEntity, Long> {
}
