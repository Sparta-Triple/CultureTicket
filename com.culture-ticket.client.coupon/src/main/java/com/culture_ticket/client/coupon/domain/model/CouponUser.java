package com.culture_ticket.client.coupon.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_coupon_user")
public class CouponUser extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;
    private String username;
    private Boolean isUsed;
    private LocalDate usedDate;

    public static CouponUser from(String username, Coupon coupon) {
        CouponUser couponUser = builder()
                .coupon(coupon)
                .username(username)
                .isUsed(false)
                .build();
        couponUser.createdBy(username);
        return couponUser;
    }
}
