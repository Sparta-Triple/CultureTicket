package com.culture_ticket.client.reservation_payment.application.dto.responseDto;

import com.culture_ticket.client.reservation_payment.domain.model.Performance;
import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.culture_ticket.client.reservation_payment.domain.model.ReservationStatus;
import com.culture_ticket.client.reservation_payment.domain.model.Seat;
import com.culture_ticket.client.reservation_payment.domain.model.TimeTable;
import com.culture_ticket.client.reservation_payment.domain.model.User;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationResponseDto {

    private UUID reservationId;
    private LocalDateTime reservationDate;
    private ReservationStatus reservationStatus;
    private String username;
    private String nickname;
    private String phone;
    private String title;
    private String venue;
    private String performanceStatus;
    private LocalDateTime performanceDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<SeatInfo> seats;

    @Builder
    private ReservationResponseDto(UUID reservationId, LocalDateTime reservationDate,
        ReservationStatus reservationStatus, String username, String nickname, String phone,
        String title, String venue, String performanceStatus, LocalDateTime performanceDate,
        LocalDateTime startTime, LocalDateTime endTime, List<SeatInfo> seats) {
        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
        this.username = username;
        this.nickname = nickname;
        this.phone = phone;
        this.title = title;
        this.venue = venue;
        this.performanceStatus = performanceStatus;
        this.performanceDate = performanceDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seats = seats;
    }

    public static ReservationResponseDto of(Reservation reservation, User user, List<Seat> seats,
        Performance performance, TimeTable timeTable) {
        return builder()
            .reservationId(reservation.getId())
            .reservationDate(reservation.getCreatedAt())
            .reservationStatus(reservation.getReservationStatus())
            .username(user.getUsername())
            .nickname(user.getNickname())
            .phone(user.getPhone())
            .title(performance.getTitle())
            .venue(performance.getVenue())
            .performanceStatus(performance.getPerformanceStatus())
            .performanceDate(timeTable.getDate())
            .startTime(timeTable.getStartTime())
            .endTime(timeTable.getEndTime())
            .seats(seats
                .stream()
                .map(SeatInfo::from)
                .toList())
            .build();
    }

    // 좌석 정보를 위한 내부 클래스
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class SeatInfo {

        private Integer seatNumber;
        private String seatClass;
        private long ticketPrice;

        @Builder
        private SeatInfo(Integer seatNumber, String seatClass, long ticketPrice) {
            this.seatNumber = seatNumber;
            this.seatClass = seatClass;
            this.ticketPrice = ticketPrice;
        }

        public static SeatInfo from(Seat seat) {
            return new SeatInfo(seat.getSeatNumber(), seat.getSeatClass(), seat.getPrice());
        }
    }
}
