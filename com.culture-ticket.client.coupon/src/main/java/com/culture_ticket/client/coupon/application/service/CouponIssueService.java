package com.culture_ticket.client.coupon.application.service;

import com.culture_ticket.client.coupon.common.CustomException;
import com.culture_ticket.client.coupon.common.ErrorType;
import com.culture_ticket.client.coupon.domain.model.Coupon;
import com.culture_ticket.client.coupon.domain.model.CouponUser;
import com.culture_ticket.client.coupon.domain.repository.CouponRepository;
import com.culture_ticket.client.coupon.domain.repository.CouponUserRepository;
import com.culture_ticket.client.coupon.infrastructure.annotation.DistributeLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CouponIssueService {

    private final CouponRepository couponRepository;
    private final CouponUserRepository couponUserRepository;

    @DistributeLock(key = "#key")
    public void couponDecrease(String key, String username, UUID couponId) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
                () -> new CustomException(ErrorType.COUPON_NOT_FOUND)
        );
        couponUserRepository.save(CouponUser.from(username, coupon));
        coupon.decrease();
        System.out.println(coupon.getAvailableStock() + "개 남았습니다.");
    }
}