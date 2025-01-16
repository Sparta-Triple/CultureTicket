package com.culture_ticket.client.coupon.application.service;

import com.culture_ticket.client.coupon.application.dto.request.CouponCreateRequestDto;
import com.culture_ticket.client.coupon.application.dto.response.CouponResponseDto;
import com.culture_ticket.client.coupon.common.util.RoleValidator;
import com.culture_ticket.client.coupon.domain.model.Coupon;
import com.culture_ticket.client.coupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponIssueService couponIssueService;
    private final CouponRepository couponRepository;

    private static final String COUPON_KEY_PREFIX = "COUPON_";

    @Transactional
    public void createCoupon(String username, String role, CouponCreateRequestDto requestDto) {
        RoleValidator.validateIsAdmin(role);
        couponRepository.save(Coupon.from(username, requestDto));
    }

    public void issueCoupon(String username, String role, UUID couponId) {
        RoleValidator.validateIsUSER(role);
        String key = COUPON_KEY_PREFIX + couponId;
        couponIssueService.issueCoupon(key, username, couponId);
    }

    @Transactional(readOnly = true)
    public List<CouponResponseDto> getUnExpiredCoupons() {
        List<Coupon> coupons = couponRepository.findAllByExpirationDateAfter(LocalDate.now());
        List<CouponResponseDto> responseDtos = coupons.stream().map(CouponResponseDto::new).toList();
        return responseDtos;
    }
}
