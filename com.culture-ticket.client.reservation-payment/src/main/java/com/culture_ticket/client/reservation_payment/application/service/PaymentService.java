package com.culture_ticket.client.reservation_payment.application.service;

import com.culture_ticket.client.reservation_payment.application.dto.kafka.KafkaSeatStatusRequestDto;
import com.culture_ticket.client.reservation_payment.application.dto.requestDto.ReservationRequestDto;
import com.culture_ticket.client.reservation_payment.application.dto.requestDto.SeatSelectionRequestDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.CreatePaymentResponseDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.PaymentResponseDto;
import com.culture_ticket.client.reservation_payment.common.CustomException;
import com.culture_ticket.client.reservation_payment.common.ErrorType;
import com.culture_ticket.client.reservation_payment.common.util.RoleValidator;
import com.culture_ticket.client.reservation_payment.domain.model.Payment;
import com.culture_ticket.client.reservation_payment.domain.model.SeatPayment;
import com.culture_ticket.client.reservation_payment.domain.model.SeatStatus;
import com.culture_ticket.client.reservation_payment.domain.repository.PaymentRepository;
import com.culture_ticket.client.reservation_payment.domain.repository.SeatPaymentRepository;
import com.culture_ticket.client.reservation_payment.infrastructure.client.PerformanceClient;
import com.culture_ticket.client.reservation_payment.infrastructure.client.TicketClient;
import com.culture_ticket.client.reservation_payment.application.dto.kafka.KafkaTicketRequestDto;
import com.culture_ticket.client.reservation_payment.application.dto.feignclient.SeatResponseDto;
import com.culture_ticket.client.reservation_payment.infrastructure.messaging.SeatStatusProducer;
import com.culture_ticket.client.reservation_payment.infrastructure.messaging.TicketCreateProducer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SeatPaymentRepository seatPaymentRepository;
    private final PerformanceClient performanceClient;
    private final TicketClient ticketClient;
    private final ReservationService reservationService;
    private final TicketCreateProducer ticketCreateProducer;
    private final SeatStatusProducer seatStatusProducer;

    /**
     * 결제 생성
     * 좌석 결제 생성
     * 좌석 상태 변경
     * 예매 생성
     * 티켓 생성
     *
     * @param userId
     * @param username
     * @param role
     * @param request
     * @return
     */
    @Transactional
    public CreatePaymentResponseDto createPayment(String userId, String username, String role, SeatSelectionRequestDto request) {
        // 역할 검증
        RoleValidator.validateIsAdminOrUser(role);

        // 좌석 목록 검증
        List<SeatResponseDto> seats = validateAndRetrieveSeats(request.getSeatIds());

        // 결제 총 금액 계산
        Long totalPrice = calculateTotalPrice(seats);

        // 결제 생성 및 저장
        Payment savedPayment = createPaymentAndSave(userId, totalPrice);

        // 좌석 결제 생성
        createSeatPayments(seats, savedPayment);

        // 좌석 상태 업데이트 (Kafka)
        updateSeatsStatus(request.getSeatIds(), username);

        // 예매 생성
        UUID reservationId = createReservation(savedPayment, userId);

        // 티켓 생성
        createTickets(reservationId, seats, userId, username, role);

        return new CreatePaymentResponseDto(totalPrice);
    }

    private List<SeatResponseDto> validateAndRetrieveSeats(List<UUID> seatIds) {
        List<SeatResponseDto> seats = new ArrayList<>();
        for (UUID seatId : seatIds) {
            SeatResponseDto seat = performanceClient.getSeat(seatId).getData();
            if (seat == null) {
                throw new CustomException(ErrorType.NOT_FOUND_SEAT);
            }
            if (seat.getSeatStatus().equals(SeatStatus.UNAVAILABLE)) {
                throw new CustomException(ErrorType.SEAT_ALREADY_RESERVED);
            }
            seats.add(seat);
        }
        return seats;
    }

    private Long calculateTotalPrice(List<SeatResponseDto> seats) {
        return seats.stream()
                .mapToLong(SeatResponseDto::getSeatPrice)
                .sum();
    }

    private Payment createPaymentAndSave(String userId, Long totalPrice) {
        Payment payment = Payment.builder()
                .userId(Long.valueOf(userId))
                .totalPrice(totalPrice)
                .build();
        return paymentRepository.save(payment);
    }

    private void createSeatPayments(List<SeatResponseDto> seats, Payment savedPayment) {
        List<SeatPayment> seatPayments = seats.stream()
                .map(seat -> SeatPayment.of(seat, savedPayment))
                .collect(Collectors.toList());
        seatPaymentRepository.saveAll(seatPayments);
    }

    private void updateSeatsStatus(List<UUID> seatIds, String username) {
        KafkaSeatStatusRequestDto requestDto = KafkaSeatStatusRequestDto.of(username, SeatStatus.UNAVAILABLE, seatIds);
        seatStatusProducer.seatStatusSend("seat-topic", requestDto);
    }

    private UUID createReservation(Payment savedPayment, String userId) {
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(savedPayment.getId(), Long.valueOf(userId));
        return reservationService.createReservation(reservationRequestDto);
    }

    private void createTickets(UUID reservationId, List<SeatResponseDto> seats, String userId, String username, String role) {
        UUID performanceId = performanceClient.getTimeTable(seats.get(0).getTimeTableId()).getData().getPerfomanceId();

        Map<UUID, Long> seatPriceMap = seats.stream()
                .collect(Collectors.toMap(SeatResponseDto::getSeatId, SeatResponseDto::getSeatPrice));

        List<Long> seatPrices = seats.stream()
                .map(seat -> seatPriceMap.get(seat.getSeatId()))
                .collect(Collectors.toList());

        KafkaTicketRequestDto kafkaTicketRequestDto = KafkaTicketRequestDto.of(performanceId, seats.stream()
                .map(SeatResponseDto::getSeatId)
                .collect(Collectors.toList()), seatPrices, reservationId, userId, username, role);

        ticketCreateProducer.ticketSend("ticket-topic", kafkaTicketRequestDto);
    }


    /**
     * 좌석 결제 취소
     * 예매 취소
     * 결제 취소
     * 좌석 상태 변경 -> kafka
     * @param requestDto
     */
    @Transactional
    public void revertPayment(KafkaTicketRequestDto requestDto) {

        // 좌석 결제 삭제
        List<SeatPayment> seatPayments = new ArrayList<>();
        for (int i = 0; i < requestDto.getSeatIds().size(); i++) {
            SeatPayment seatPayment = seatPaymentRepository.findBySeatId(requestDto.getSeatIds().get(i)).
                orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_SEATPAYMENT));
            seatPayment.deleted(requestDto.getUsername());
            seatPayments.add(seatPayment);
        }

        // 예매 삭제
        reservationService.revertReservation(requestDto);

        // 결제 취소
        Payment payment = paymentRepository.findById(seatPayments.get(0).getPayment().getId()).
            orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PAYMENT));
        payment.deleted(requestDto.getUsername());

        // 좌석 상태 변경(feign client)
//        performanceClient.updateSeatsStatusAvailable(
//            requestDto.getUsername(), SeatStatus.AVAILABLE, requestDto.getSeatIds());
        // 좌석 상태 변경(kafka)
        KafkaSeatStatusRequestDto kafkaSeatStatusRequestDto = KafkaSeatStatusRequestDto.of(requestDto.getUsername(), SeatStatus.UNAVAILABLE, requestDto.getSeatIds());
        seatStatusProducer.seatStatusSend("seat-topic", kafkaSeatStatusRequestDto);
    }

    public List<PaymentResponseDto> getPaymentList(Long userId) {
        List<Payment> payments = paymentRepository.findByUserId(userId);

        return payments.stream()
            .map(payment -> new PaymentResponseDto(payment.getId(), payment.getTotalPrice()))
            .collect(Collectors.toList());
    }

    public PaymentResponseDto getPayment(Long userId, UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_PAYMENT));

        if (!payment.getUserId().equals(userId)) {
            // 결제 정보가 해당 유저의 것이 아닐 경우
            throw new CustomException(ErrorType.FORBIDDEN_ACCESS);
        }
        return new PaymentResponseDto(payment.getId(), payment.getTotalPrice());
    }

//    // 더미 좌석 정보 생성
//    private List<SeatResponse> getSeats(List<UUID> seatIds) {
//        List<SeatResponse> dummySeats = List.of(
//            new SeatResponse(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), "VIP", 100000L,
//                true),
//            new SeatResponse(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"), "Standard",
//                50000L, true),
//            new SeatResponse(UUID.fromString("550e8400-e29b-41d4-a716-446655440002"), "Economy",
//                55000L, true)
//        );
//
//        // 요청한 좌석 ID에 해당하는 좌석만 필터링
//        return dummySeats.stream()
//            .filter(seat -> seatIds.contains(seat.getSeatId()))
//            .toList();
//    }
}
