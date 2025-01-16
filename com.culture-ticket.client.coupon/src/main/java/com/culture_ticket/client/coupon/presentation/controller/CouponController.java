package com.culture_ticket.client.coupon.presentation.controller;

import com.culture_ticket.client.coupon.application.dto.request.CouponCreateRequestDto;
import com.culture_ticket.client.coupon.application.dto.response.CouponResponseDto;
import com.culture_ticket.client.coupon.application.service.CouponService;
import com.culture_ticket.client.coupon.common.ResponseDataDto;
import com.culture_ticket.client.coupon.common.ResponseMessageDto;
import com.culture_ticket.client.coupon.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    // 쿠폰 생성
    @PostMapping
    public ResponseMessageDto createCoupon(
            @RequestHeader(value = "X-User-Name") String username,
            @RequestHeader(value = "X-User-Role") String role,
            @RequestBody CouponCreateRequestDto requestDto
    ) {
        couponService.createCoupon(username, role, requestDto);
        return new ResponseMessageDto(ResponseStatus.CREATE_COUPON_SUCCESS);
    }

    // 쿠폰 발급
    @PostMapping("/{couponId}")
    public ResponseMessageDto issueCoupon(
            @RequestHeader(value = "X-User-Name") String username,
            @RequestHeader(value = "X-User-Role") String role,
            @PathVariable UUID couponId
    ) {
        couponService.issueCoupon(username, role, couponId);
        return new ResponseMessageDto(ResponseStatus.ISSUE_COUPON_SUCCESS);
    }

    // 만료되지 않은 쿠폰 목록 조회
    @GetMapping
    public ResponseDataDto<List<CouponResponseDto>> getUnExpiredCoupons() {
        List<CouponResponseDto> unExpiredCoupons = couponService.getUnExpiredCoupons();
        return new ResponseDataDto<>(ResponseStatus.GET_UNEXPIRED_COUPONS_SUCCESS, unExpiredCoupons);
    }

    // 쿠폰 단일 조회
    @GetMapping("/{couponId}")
    public ResponseDataDto<CouponResponseDto> getCoupon(@PathVariable UUID couponId){
        CouponResponseDto responseDto = couponService.getCoupon(couponId);
        return new ResponseDataDto<>(ResponseStatus.GET_COUPON_SUCCESS,responseDto);
    }
}
