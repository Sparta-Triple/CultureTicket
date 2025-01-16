package com.culture_ticket.client.coupon.domain.model;

import com.culture_ticket.client.coupon.application.dto.request.CouponCreateRequestDto;
import com.culture_ticket.client.coupon.common.CustomException;
import com.culture_ticket.client.coupon.common.ErrorType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_coupon")
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Double discountRate;
    private Long discountPrice;
    private Long maxDiscountPrice;
    private Integer availableStock;
    private LocalDateTime expirationDate;

    public static Coupon from(String username, CouponCreateRequestDto requestDto){
        Coupon coupon = builder().name(requestDto.getName())
                .discountRate(requestDto.getDiscountRate())
                .discountPrice(requestDto.getDiscountPrice())
                .maxDiscountPrice(requestDto.getMaxDiscountPrice())
                .availableStock(requestDto.getAvailableStock())
                .expirationDate(requestDto.getExpirationDate())
                .build();
        coupon.createdBy(username);
        return coupon;
    }

    public void decrease() {
        validateStockCount();
        this.availableStock -= 1;
    }

    private void validateStockCount() {
        if (availableStock < 1) {
            throw new CustomException(ErrorType.COUPON_STOCK_UNAVAILABLE);
        }
    }

    public void updated(String username){
        updatedBy(username);
    }

    public void softDeleted(String username){
        softDeletedBy(username);
    }

    public void restored(String username){
        restoreBy(username);
    }
}
