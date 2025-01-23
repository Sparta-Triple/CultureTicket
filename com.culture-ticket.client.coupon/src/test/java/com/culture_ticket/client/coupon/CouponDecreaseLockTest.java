package com.culture_ticket.client.coupon;

import com.culture_ticket.client.coupon.application.dto.request.CouponCreateRequestDto;
import com.culture_ticket.client.coupon.application.service.CouponService;
import com.culture_ticket.client.coupon.domain.model.Coupon;
import com.culture_ticket.client.coupon.domain.repository.CouponRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

//@DisplayName("Redisson Lock 쿠폰 차감 테스트")
//@SpringBootTest
//class CouponDecreaseLockTest {
//
//    @Autowired
//    private CouponService couponService;
//
//    @Autowired
//    private CouponRepository couponRepository;
//
//    private Coupon coupon;
//
//    @BeforeEach
//    void setUp() {
//        coupon = Coupon.from("yshong1998",
//                new CouponCreateRequestDto(
//                        "30퍼 할인 쿠폰",
//                        30.0,
//                        0L,
//                        30000L,
//                        100,
//                        LocalDate.now()
//                ));
//        couponRepository.save(coupon);
//    }
//
////    @AfterEach
////    void teardown() {
////        couponRepository.deleteAll();
////    }
//
//    @Test
//    void 쿠폰차감_동시성100명_테스트() throws InterruptedException {
//        int numberOfThreads = 100;
//        ExecutorService executorService = Executors.newFixedThreadPool(32);
//        CountDownLatch latch = new CountDownLatch(numberOfThreads);
//        for (int i=0; i<numberOfThreads; i++) {
//            final int k = i;
//            executorService.submit(() -> {
//                try {
//                    couponService.issueCoupon("testUser" + k,"ADMIN",coupon.getId());
//                } finally {
//                    latch.countDown();
//                }
//            });
//        }
//
//        latch.await();
//
//        Coupon persistCoupon = couponRepository.findById(coupon.getId())
//                .orElseThrow(IllegalArgumentException::new);
//
//        assertThat(persistCoupon.getAvailableStock()).isZero();
//    }
//}