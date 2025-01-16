package com.culture_ticket.client.coupon.application.service;

import com.culture_ticket.client.coupon.domain.medel.Coupon;
import com.culture_ticket.client.coupon.domain.repository.CouponRepository;
import com.culture_ticket.client.coupon.infrastructure.annotation.DistributeLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponDecreaseService {
    private final CouponRepository couponRepository;

    @DistributeLock(key = "#key")
    public void couponDecrease(String key, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(IllegalArgumentException::new);

        coupon.decrease();
        System.out.println(coupon.getAvailableStock() + "개 남았습니다.");
    }
}