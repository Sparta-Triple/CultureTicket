package com.culture_ticket.client.coupon.application.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponCreateRequestDto {

    private String name;
    private Double discountRate;
    private Long discountPrice;
    private Long maxDiscountPrice;
    private Long availableStock;
    private LocalDateTime expirationDate;

}
