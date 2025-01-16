package com.culture_ticket.client.coupon.domain.repository;

import com.culture_ticket.client.coupon.domain.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {
}
