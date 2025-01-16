package com.culture_ticket.client.coupon.domain.repository;

import com.culture_ticket.client.coupon.domain.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
