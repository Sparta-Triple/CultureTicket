package com.culture_ticket.client.reservation_payment.application.service;

import com.culture_ticket.client.reservation_payment.application.dto.requestDto.ReservationRequestDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.ReservationResponseDto;
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
import com.culture_ticket.client.reservation_payment.domain.repository.SeatPaymentRepository;
import com.culture_ticket.client.reservation_payment.infrastructure.client.PerformanceClient;
import com.culture_ticket.client.reservation_payment.infrastructure.client.UserClient;
import com.querydsl.core.types.Predicate;
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
    private final SeatPaymentRepository seatPaymentRepository;
    private final PaymentRepository paymentRepository;
    private final UserClient userClient;
    private final PerformanceClient performanceClient;

    /**
     * 예약 생성
     *
     * @param request
     */
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

    /**
     * 예약 내역 전체 조회
     *
     * @param role
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ReservationResponseDto> getReservations(String role, Pageable pageable) {
        if (!role.equals("ADMIN")) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        isReservationInPage(reservationPage);

        return toReservationResponseDto(reservationPage);
    }

    /**
     * 예약 내역 단일 조회
     *
     * @param userId
     * @param role
     * @param reservationId
     * @return
     */
    @Transactional(readOnly = true)
    public ReservationResponseDto getReservation(String userId, String role, UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_RESERVATION));

        if (!role.equals("ADMIN") && !role.equals("USER")
            || !userId.equals(reservation.getUserId().toString())) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        return getReservationResponseDto(reservation);
    }

    /**
     * 내 예약 내역 조회
     *
     * @param userId
     * @param role
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ReservationResponseDto> getMeReservation(String userId, String role,
        Pageable pageable) {
        if (!role.equals("USER")) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(userId,
            pageable);

        isReservationInPage(reservationPage);

        // 조회하려는 데이터가 로그인유저가 생성한 건지 확인
        Reservation reservation = reservationPage.getContent().get(0);
        if (!userId.equals(reservation.getUserId().toString())) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        return toReservationResponseDto(reservationPage);
    }

    /**
     * 예약 내역 검색
     *
     * @param userId
     * @param role
     * @param pageable
     * @param predicate
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ReservationResponseDto> searchReservation(String userId, String role, Pageable pageable, Predicate predicate) {
        if (!role.equals("ADMIN") && !role.equals("USER")) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        Page<Reservation> reservationPage = null;
        if(role.equals("USER")) {
            reservationPage = reservationRepository.findAllByUserId(userId, predicate, pageable);
        } else {
            reservationPage = reservationRepository.findAll(predicate, pageable);
        }

        isReservationInPage(reservationPage);

        return toReservationResponseDto(reservationPage);
    }

    /**
     * 예약 취소(삭제)
     *
     * @param userId
     * @param username
     * @param role
     * @param reservationId
     */
    @Transactional
    public void deleteReservation(String userId, String username, String role, UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_RESERVATION));

        if (!role.equals("ADMIN") && !role.equals("USER")
            || !userId.equals(reservation.getUserId().toString())) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        // TODO: 좌석 상태 예매가능으로 변경

        // 좌석 결제 삭제
        reservation.getPayment().getSeatPayments().stream().forEach(seatPayment -> {
            SeatPayment seatPaymentEntity = seatPaymentRepository.findById(
                    seatPayment.getId()) // TODO: 이거 서비스를 주입받아서 해야하는지 엔티티를 주입받아 해야하는지 의논
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_SEATPAYMENT));

            seatPaymentEntity.deleted(username);
        });

        // 결제 삭제
        Payment payment = reservation.getPayment();
        payment.deleted(username);

        // 예약 삭제
        reservation.deleted(username);
    }

    private static void isReservationInPage(Page<Reservation> reservationPage) {
        if (!reservationPage.hasContent()) {
            throw new CustomException(ErrorType.NOT_FOUND_RESERVATION);
        }
    }

    private Page<ReservationResponseDto> toReservationResponseDto(
        Page<Reservation> reservationPage) {
        Page<ReservationResponseDto> responseDtoPage = reservationPage.map(reservation -> {
            return getReservationResponseDto(reservation);
        });
        return responseDtoPage;
    }

    private ReservationResponseDto getReservationResponseDto(Reservation reservation) {
        Long userId = reservation.getUserId();
        User user = userClient.getUser(userId).getData();

        // seatPayment 정보 가져오기
        Payment payment = reservation.getPayment();
        List<SeatPayment> seatPayments = payment.getSeatPayments();

        // 좌석 정보 가져오기
        List<Seat> seats = seatPayments.stream()
            .map(seatPayment -> {
                Seat seat = performanceClient.getSeat(seatPayment.getSeatId()).getData();
                return Seat.from(seat);
            })
            .collect(Collectors.toList());

        // 공연과 타임테이블 정보 가져오기
        Performance performance = null;
        TimeTable timeTable = null;
        if (!seats.isEmpty()) {
            UUID timeTableId = seats.get(0).getTimeTableId();
            timeTable = performanceClient.getTimeTable(timeTableId).getData();
            performance = performanceClient.getPerfomance(timeTable.getPerfomanceId()).getData();
        }

        return ReservationResponseDto.of(reservation, user, seats, performance, timeTable);
    }
}
