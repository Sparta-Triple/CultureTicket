package com.culture_ticket.client.reservation_payment.application.service;

import com.culture_ticket.client.reservation_payment.application.dto.requestDto.ReservationRequest;
import com.culture_ticket.client.reservation_payment.common.CustomException;
import com.culture_ticket.client.reservation_payment.common.ErrorType;
import com.culture_ticket.client.reservation_payment.domain.model.Payment;
import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.culture_ticket.client.reservation_payment.domain.repository.PaymentRepository;
import com.culture_ticket.client.reservation_payment.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;

    public void createReservation(ReservationRequest request) {

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
