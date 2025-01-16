package com.culture_ticket.client.coupon.domain.repository;

import com.culture_ticket.client.coupon.domain.model.CouponUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CouponUserRepository extends JpaRepository<CouponUser, UUID> {

    List<CouponUser> findAllByUsernameAndIsUsedFalse(String username);

}
