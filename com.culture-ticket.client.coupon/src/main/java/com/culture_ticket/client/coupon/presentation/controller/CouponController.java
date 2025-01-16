package com.culture_ticket.client.coupon.presentation.controller;

import com.culture_ticket.client.coupon.application.dto.request.CouponCreateRequestDto;
import com.culture_ticket.client.coupon.application.service.CouponService;
import com.culture_ticket.client.coupon.common.ResponseMessageDto;
import com.culture_ticket.client.coupon.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseMessageDto createCoupon(
            @RequestHeader(value = "X-User-Name") String username,
            @RequestHeader(value = "X-User-Role") String role,
            @RequestBody CouponCreateRequestDto requestDto
    ) {
        couponService.createCoupon(username, role, requestDto);
        return new ResponseMessageDto(ResponseStatus.CREATE_COUPON_SUCCESS);
    }

    @PutMapping("/{couponId}")
    public ResponseMessageDto issueCoupon(
            @RequestHeader(value = "X-User-Name") String username,
            @RequestHeader(value = "X-User-Role") String role,
            @PathVariable UUID couponId
    ) {
        couponService.issueCoupon(username, role, couponId);
        return new ResponseMessageDto(ResponseStatus.ISSUE_COUPON_SUCCESS);
    }
}
