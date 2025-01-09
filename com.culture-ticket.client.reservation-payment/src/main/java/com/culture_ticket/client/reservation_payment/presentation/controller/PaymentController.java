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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseDataDto<CreatePaymentResponseDto> createPayment(
        @RequestHeader(value = "X-User-Id") String userId,
        @RequestHeader(value = "X-User-Name", required = true) String username,
        @RequestHeader(value = "X-User-Role", required = true) String role,
        @Valid @RequestBody SeatSelectionRequestDto request) {
        return new ResponseDataDto<>(ResponseStatus.PAYMENT_SUCCESS,
            paymentService.createPayment(userId, username, role, request));
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
