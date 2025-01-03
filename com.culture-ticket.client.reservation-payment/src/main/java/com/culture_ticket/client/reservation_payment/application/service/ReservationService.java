package com.culture_ticket.client.reservation_payment.application.service;

import com.culture_ticket.client.reservation_payment.application.dto.responseDto.ReservationResponseDto;
import com.culture_ticket.client.reservation_payment.application.dto.requestDto.ReservationRequestDto;
import com.culture_ticket.client.reservation_payment.common.CustomException;
import com.culture_ticket.client.reservation_payment.common.ErrorType;
import com.culture_ticket.client.reservation_payment.domain.model.Payment;
import com.culture_ticket.client.reservation_payment.domain.model.Performance;
import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.culture_ticket.client.reservation_payment.domain.model.Seat;
import com.culture_ticket.client.reservation_payment.domain.model.SeatPayment;
import com.culture_ticket.client.reservation_payment.domain.model.TimeTable;
import com.culture_ticket.client.reservation_payment.domain.model.User;
import com.culture_ticket.client.reservation_payment.domain.repository.PaymentRepository;
import com.culture_ticket.client.reservation_payment.domain.repository.ReservationRepository;
import com.culture_ticket.client.reservation_payment.infrastructure.client.PerformanceClient;
import com.culture_ticket.client.reservation_payment.infrastructure.client.SeatClient;
import com.culture_ticket.client.reservation_payment.infrastructure.client.TimeTableClient;
import com.culture_ticket.client.reservation_payment.infrastructure.client.UserClient;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final UserClient userClient;
    private final PerformanceClient performanceClient;
    private final TimeTableClient timeTableClient;
    private final SeatClient seatClient;

    /**
     * 예약 내역 전체 조회
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ReservationResponseDto> getReservation(Pageable pageable) {
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        // reservationPage 하나 당 데이터 가져오기
        Page<ReservationResponseDto> responseDtoPage = reservationPage.map(reservation -> {
            Long userId = reservation.getUserId();
            User user = userClient.getUser(userId).getData();

            // seatPayment 정보 가져오기
            Payment payment = reservation.getPayment();
            List<SeatPayment> seatPayments = payment.getSeatPayments();

            // 좌석 정보 가져오기
            List<Seat> seats = seatPayments.stream()
                .map(seatPayment -> {
                    Seat seat = seatClient.getSeat(seatPayment.getSeatId()).getData();
                    return Seat.from(seat);
                })
                .collect(Collectors.toList());

            // 공연과 타임테이블 정보 가져오기
            Performance performance = null;
            TimeTable timeTable = null;
            if (!seats.isEmpty()) {
                UUID timeTableId = seats.get(0).getTimeTableId();
                timeTable = timeTableClient.getTimeTable(timeTableId).getData();
                performance = performanceClient.getPerfomance(timeTable.getPerfomanceId()).getData();
            }

            return ReservationResponseDto.of(reservation, user, seats, performance, timeTable);
        });

        return responseDtoPage;
    }

    public void createReservation(ReservationRequestDto request) {

        Payment payment = paymentRepository.findById(request.getPaymentId())
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PAYMENT));

        Reservation reservation = Reservation.builder()
            .userId(request.getUserId())
            .payment(payment)
            .build();
        reservationRepository.save(reservation);
        //TODO 예매를 생성한 뒤 좌석 상태 비활성화 처리
    }
}
