package com.culture_ticket.client.coupon.application.service;

import com.culture_ticket.client.coupon.application.dto.request.CouponCreateRequestDto;
import com.culture_ticket.client.coupon.common.util.RoleValidator;
import com.culture_ticket.client.coupon.domain.model.Coupon;
import com.culture_ticket.client.coupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponDecreaseService couponDecreaseService;
    private final CouponRepository couponRepository;

    private static final String COUPON_KEY_PREFIX = "COUPON_";

    public void decrease(Long couponId) {
        String key = COUPON_KEY_PREFIX + couponId;
        couponDecreaseService.couponDecrease(key, couponId);
    }

    @Transactional
    public void createCoupon(String username, String role, CouponCreateRequestDto requestDto) {
        RoleValidator.validateIsAdmin(role);
        couponRepository.save(Coupon.of(username, requestDto));
    }
}
