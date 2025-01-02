package com.culture_ticket.client.reservation_payment.presentation.controller;

import com.culture_ticket.client.reservation_payment.application.dto.requestDto.SeatSelectionRequest;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.CreatePaymentResponse;
import com.culture_ticket.client.reservation_payment.application.service.PaymentService;
import com.culture_ticket.client.reservation_payment.common.ResponseDataDto;
import com.culture_ticket.client.reservation_payment.common.ResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<ResponseDataDto<CreatePaymentResponse>> createPayment(
        @Valid @RequestBody SeatSelectionRequest request) { //TODO 헤더 데이터 추가
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.PAYMENT_SUCCESS,
            paymentService.createPayment(1L, request)));
    }
}
