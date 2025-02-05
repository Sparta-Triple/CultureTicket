package com.culture_ticket.client.performance.domain.model;

import com.culture_ticket.client.performance.application.dto.requestDto.SeatCreateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_seat")
@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public class Seat extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String seatClass;
  private Integer seatNumber;
  private Long seatPrice;
  @Enumerated(EnumType.STRING)
  private SeatStatus seatStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  private TimeTable timeTable;

  public static List<Seat> of(TimeTable timeTable, SeatCreateRequestDto requestDto, String username){
    List<Seat> seats = new ArrayList<>();
    for (int seatNumber = 1; seatNumber <= requestDto.getCount(); seatNumber++) {
      Seat seat = builder()
          .seatClass(requestDto.getSeatClass())
          .seatNumber(seatNumber)
          .seatPrice(requestDto.getPrice())
          .seatStatus(SeatStatus.AVAILABLE)
          .timeTable(timeTable)
          .build();
      seat.createdBy(username);
      seats.add(seat);
    }
    return seats;
  }

  public void updateStatus(String username, SeatStatus seatStatus) {
    updatedBy(username);
    this.seatStatus = seatStatus;
  }

  public void deleted(String username) {
    softDeletedBy(username);
  }

  public void restore(String username) {
    restoreBy(username);
  }

  public void updatePrice(Long newPrice, String username) {
    this.seatPrice = newPrice;
    updatedBy(username);
  }
}
