package com.culture_ticket.client.reservation_payment.application.service;

import com.culture_ticket.client.reservation_payment.application.dto.requestDto.ReservationRequestDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.RefundPriceResponseDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.ReservationResponseDto;
import com.culture_ticket.client.reservation_payment.common.CustomException;
import com.culture_ticket.client.reservation_payment.common.ErrorType;
import com.culture_ticket.client.reservation_payment.common.util.RoleValidator;
import com.culture_ticket.client.reservation_payment.domain.model.Payment;
import com.culture_ticket.client.reservation_payment.infrastructure.client.TicketClient;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.PerformanceResponseDto;
import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.SeatResponseDto;
import com.culture_ticket.client.reservation_payment.domain.model.SeatPayment;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.TimeTableResponseDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.UserResponseDto;
import com.culture_ticket.client.reservation_payment.domain.repository.PaymentRepository;
import com.culture_ticket.client.reservation_payment.domain.repository.ReservationRepository;
import com.culture_ticket.client.reservation_payment.domain.repository.SeatPaymentRepository;
import com.culture_ticket.client.reservation_payment.infrastructure.client.PerformanceClient;
import com.culture_ticket.client.reservation_payment.infrastructure.client.UserClient;
import java.time.LocalDateTime;
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
    private final TicketClient ticketClient;

    /**
     * 예약 생성
     *
     * @param request
     */
    public UUID createReservation(ReservationRequestDto request) {

        Payment payment = paymentRepository.findById(request.getPaymentId())
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PAYMENT));

        Reservation reservation = Reservation.builder()
            .userId(request.getUserId())
            .payment(payment)
            .build();
        Reservation saveReservation = reservationRepository.save(reservation);

        return saveReservation.getId();
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
        RoleValidator.validateIsAdmin(role);

        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

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

        RoleValidator.validateIsAdminOrUser(role);

        if (role.equals("USER")) {
            assertUserIsOwner(userId, reservation);
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
        RoleValidator.validateIsUser(role);

        Page<Reservation> reservationPage = reservationRepository.
            findAllByUserIdAndIsDeletedFalse(userId, pageable);

        // 조회하려는 데이터가 로그인유저가 생성한 건지 확인
        if (!reservationPage.hasContent()) {
            Reservation reservation = reservationPage.getContent().get(0);
            assertUserIsOwner(userId, reservation);
        }

        return toReservationResponseDto(reservationPage);
    }

    /**
     * 예약 내역 검색
     *
     * @param userId
     * @param role
     * @param pageable
     * @param dateRange
     * @return
     */
    @Transactional(readOnly = true)
    public Page<ReservationResponseDto> searchReservation(String userId, String role,
        Pageable pageable, String dateRange) {
        if (!role.equals("ADMIN") && !role.equals("USER")) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        LocalDateTime startDate = null;
        LocalDateTime endDate = LocalDateTime.now();

        switch (dateRange) {
            case "1month":
                startDate = endDate.minusMonths(1);
                break;
            case "3month":
                startDate = endDate.minusMonths(3);
                break;
            case "6month":
                startDate = endDate.minusMonths(6);
                break;
            case "1year":
                startDate = endDate.minusYears(1);
                break;
            default:
                throw new CustomException(ErrorType.BAD_REQUEST_SEARCH);
        }

        Page<Reservation> reservationPage = null;
        if (role.equals("USER")) {
            reservationPage = reservationRepository.
                findAllByUserIdAndDateRange(userId, pageable, startDate, endDate);
        } else {
            reservationPage = reservationRepository.
                findAllByDateRange(pageable, startDate, endDate);
        }

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
    public RefundPriceResponseDto deleteReservation(String userId, String username, String role, UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_RESERVATION));

        RoleValidator.validateIsAdminOrUser(role);

        if (role.equals("USER")) {
            assertUserIsOwner(userId, reservation);
        }

        // TODO: 좌석 상태 예매가능으로 변경(feign client 사용 -> kafka 사용)
        // 좌석 예매 가능으로 변경
        List<UUID> seatIds = reservation.getPayment().getSeatPayments().stream().map(seatPayment ->
            seatPayment.getSeatId()).toList();
        performanceClient.
            updateSeatsStatusAvailable(username, "AVAILABLE", seatIds);


        // 좌석 결제 삭제
        reservation.getPayment().getSeatPayments().stream().forEach(seatPayment -> {
            SeatPayment seatPaymentEntity = seatPaymentRepository.findById(
                    seatPayment.getId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_SEATPAYMENT));

            seatPaymentEntity.deleted(username);
        });

        // 결제 삭제
        Payment payment = reservation.getPayment();

        // 환불액
        Long refundPrice = payment.getTotalPrice();

        payment.deleted(username);

        // 티켓 삭제
        ticketClient.deleteTicket(username, role, reservationId);

        // 예약 삭제
        reservation.deleted(username);

        return RefundPriceResponseDto.from(refundPrice);
    }

    // 로그인 유저가 데이터의 생성자인지 확인
    private static void assertUserIsOwner(String userId, Reservation reservation) {
        if (!userId.equals(reservation.getUserId().toString())) {
            throw new CustomException(ErrorType.UNAUTHORIZED_ACCESS);
        }
    }

    // Page<Entity> -> Page<ResponseDto>
    private Page<ReservationResponseDto> toReservationResponseDto(
        Page<Reservation> reservationPage) {
        Page<ReservationResponseDto> responseDtoPage = reservationPage.map(
            this::getReservationResponseDto);
        return responseDtoPage;
    }

    // feign client로 ResponseDto에 필요한 데이터 가져옴
    private ReservationResponseDto getReservationResponseDto(Reservation reservation) {
        Long userId = reservation.getUserId();
        UserResponseDto user = userClient.getUser(userId).getData();

        // seatPayment 정보 가져오기
        List<SeatPayment> seatPayments = reservation.getPayment().getSeatPayments();

        // 좌석 정보 가져오기
        List<SeatResponseDto> seats = seatPayments.stream()
            .map(seatPayment -> {
                return performanceClient.getSeat(seatPayment.getSeatId()).getData();
                // SeatResponseDto.from(seat);
            })
            .collect(Collectors.toList());

        // 공연과 타임테이블 정보 가져오기
        PerformanceResponseDto performance = null;
        TimeTableResponseDto timeTable = null;
        if (!seats.isEmpty()) {
            UUID timeTableId = seats.get(0).getTimeTableId();
            timeTable = performanceClient.getTimeTable(timeTableId).getData();
            performance = performanceClient.getPerfomance(timeTable.getPerfomanceId()).getData();
        }

        return ReservationResponseDto.of(reservation, user, seats, performance, timeTable);
    }
}
