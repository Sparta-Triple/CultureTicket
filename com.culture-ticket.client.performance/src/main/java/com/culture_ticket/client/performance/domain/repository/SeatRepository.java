package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.domain.model.Seat;
import com.culture_ticket.client.performance.domain.model.TimeTable;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, UUID> {

  List<Seat> findAllByTimeTable(TimeTable timeTable);
  List<Seat> findAllByTimeTableAndSeatClass(TimeTable timeTable, String seatClass);

}
