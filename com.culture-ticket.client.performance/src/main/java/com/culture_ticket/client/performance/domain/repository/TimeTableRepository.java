package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.domain.model.TimeTable;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, UUID>, TimeTableRepositoryCustom {

}
