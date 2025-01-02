package com.culture_ticket.client.reservation_payment.application.dto;

import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.culture_ticket.client.reservation_payment.domain.model.ReservationStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResGetReservationDto {
    private LocalDateTime reservationDate; // TODO: 이름 reservationDate가 낫지 않을까?
    private ReservationStatus reservationStatus;
    private String email;
    private String username;
    private String phone;
    private String title;
    private String venue;
    private String performanceStatus;
    private LocalDateTime performanceDate;
    private Long ticketPrice;

    @Builder
    private ResGetReservationDto(LocalDateTime reservationDate, ReservationStatus reservationStatus, String email, String username, String phone,
                                 String title, String venue, String performanceStatus, LocalDateTime performanceDate, Long ticketPrice) {
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.title = title;
        this.venue = venue;
        this.performanceStatus = performanceStatus;
        this.performanceDate = performanceDate;
        this.ticketPrice = ticketPrice;
    }

    public static ResGetReservationDto from(Reservation reservation) {
        return builder()
                .reservationDate(reservation.getCreatedAt())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }
}
