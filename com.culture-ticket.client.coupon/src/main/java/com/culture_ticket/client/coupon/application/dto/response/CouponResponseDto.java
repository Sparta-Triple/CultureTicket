package com.culture_ticket.client.coupon.application.dto.response;

import com.culture_ticket.client.coupon.domain.model.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class CouponResponseDto {

    private UUID id;

    private String name;
    private Double discountRate;
    private Long discountPrice;
    private Long maxDiscountPrice;
    private Integer availableStock;
    private LocalDate expirationDate;

    public CouponResponseDto(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.discountRate = coupon.getDiscountRate();
        this.discountPrice = coupon.getDiscountPrice();
        this.maxDiscountPrice = coupon.getMaxDiscountPrice();
        this.availableStock = coupon.getAvailableStock();
        this.expirationDate = coupon.getExpirationDate();
    }
}
