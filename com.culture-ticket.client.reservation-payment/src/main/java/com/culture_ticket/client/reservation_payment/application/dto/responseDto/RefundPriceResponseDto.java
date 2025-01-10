package com.culture_ticket.client.reservation_payment.application.dto.responseDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundPriceResponseDto {

    private Long refundPrice;

    @Builder
    private RefundPriceResponseDto(Long refundPrice) {
        this.refundPrice = refundPrice;
    }

    public static RefundPriceResponseDto from(Long refundPrice) {
        return builder()
            .refundPrice(refundPrice)
            .build();
    }
}
