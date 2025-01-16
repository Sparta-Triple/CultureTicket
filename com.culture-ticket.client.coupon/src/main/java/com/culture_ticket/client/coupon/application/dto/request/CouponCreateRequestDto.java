package com.culture_ticket.client.coupon.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponCreateRequestDto {

    private String name;
    private Double discountRate;
    private Long discountPrice;
    private Long maxDiscountPrice;
    private Integer availableStock;
    private LocalDateTime expirationDate;

}
