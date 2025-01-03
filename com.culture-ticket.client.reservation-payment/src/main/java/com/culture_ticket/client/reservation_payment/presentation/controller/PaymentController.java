package com.culture_ticket.client.reservation_payment.presentation.controller;

import com.culture_ticket.client.reservation_payment.application.dto.requestDto.SeatSelectionRequestDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.CreatePaymentResponseDto;
import com.culture_ticket.client.reservation_payment.application.dto.responseDto.PaymentResponseDto;
import com.culture_ticket.client.reservation_payment.application.service.PaymentService;
import com.culture_ticket.client.reservation_payment.common.ResponseDataDto;
import com.culture_ticket.client.reservation_payment.common.ResponseStatus;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseDataDto<CreatePaymentResponseDto> createPayment(
        @Valid @RequestBody SeatSelectionRequestDto request) { //TODO 헤더 데이터 추가
        return new ResponseDataDto<>(ResponseStatus.PAYMENT_SUCCESS,
            paymentService.createPayment(1L, request));
    }

    @GetMapping("")
    public ResponseDataDto<List<PaymentResponseDto>> getPaymentList() {
        return new ResponseDataDto<>(ResponseStatus.GET_PAYMENT_SUCCESS,
            paymentService.getPaymentList(1L));
    }

    @GetMapping("/{paymentId}")
    public ResponseDataDto<PaymentResponseDto> getPayment(
        @PathVariable UUID paymentId) {
        return new ResponseDataDto<>(ResponseStatus.GET_PAYMENT_SUCCESS,
            paymentService.getPayment(1L, paymentId));
    }
}
