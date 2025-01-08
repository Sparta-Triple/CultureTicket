package com.culture_ticket.client.reservation_payment.application.service;

import com.culture_ticket.client.reservation_payment.application.dto.requestDto.ReservationRequestDto;
import com.culture_ticket.client.reservation_payment.application.dto.requestDto.SeatSelectionRequestDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.CreatePaymentResponseDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.PaymentResponseDto;
import com.culture_ticket.client.reservation_payment.common.CustomException;
import com.culture_ticket.client.reservation_payment.common.ErrorType;
import com.culture_ticket.client.reservation_payment.domain.model.Payment;
import com.culture_ticket.client.reservation_payment.domain.model.SeatPayment;
import com.culture_ticket.client.reservation_payment.domain.repository.PaymentRepository;
import com.culture_ticket.client.reservation_payment.domain.repository.SeatPaymentRepository;
import com.culture_ticket.client.reservation_payment.infrastructure.client.PerformanceClient;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.SeatResponseDto;
import java.util.ArrayList;
import java.util.List;
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
    private final ReservationService reservationService;

    @Transactional
    public CreatePaymentResponseDto createPayment(String userId, SeatSelectionRequestDto request) {
        // 좌석 유효성 검사
        // 타임 테이블을 통해서 좌석 데이터를 아예 가져와서 좌석 상태랑 존재 여부 확인 ?

        List<UUID> seatIds = request.getSeatIds();
        List<SeatResponseDto> seats = new ArrayList<>();

        for (UUID seatId : seatIds) {
            SeatResponseDto seat = performanceClient.getSeat(seatId).getData();
            if (seat == null) {
                throw new CustomException(ErrorType.NOT_FOUND_SEAT); // TODO: 추후 fallback함수로 빼기
            }
            seats.add(seat);
        }

        // 좌석 금액 가져오기
        Long totalPrice = seats.stream()
            .mapToLong(SeatResponseDto::getPrice)
            .sum();

        // 결제 생성
        Payment payment = Payment.builder()
            .userId(Long.valueOf(userId))
            .totalPrice(totalPrice)
            .build();

        Payment savedPayment = paymentRepository.save(payment);

        // 좌석 결제 생성
        List<SeatPayment> seatPayments = seats.stream()
            .map(seat -> SeatPayment.of(seat, savedPayment))
            .collect(Collectors.toList());// 리스트로 수집
        seatPaymentRepository.saveAll(seatPayments);

        // TODO: feign client로 좌석 예매 불가로 변경

        // 예매 생성
        reservationService.createReservation(new ReservationRequestDto(savedPayment.getId(), Long.valueOf(userId)));
        return new CreatePaymentResponseDto(totalPrice);
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
