package com.culture_ticket.client.coupon.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    // 사용 예시
//  LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
//    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
//    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
//    GET_USER_SUCCESS(HttpStatus.OK, "유저 조회에 성공했습니다."),
//    UPDATE_USER_SUCCESS(HttpStatus.OK, "유저 정보 수정에 성공했습니다."),
//    DELETE_USER_SUCCESS(HttpStatus.OK, "회원 탈퇴에 성공했습니다."),
//    SEARCH_USER_SUCCESS(HttpStatus.OK, "회원 검색에 성공했습니다."),
//
//    CREATE_DELIVERY_MANAGER_SUCCESS(HttpStatus.CREATED, "배송 담당자 등록에 성공했습니다."),
//    GET_DELIVERY_MANAGER_SUCCESS(HttpStatus.OK, "배송 담당자 조회에 성공했습니다."),
//    PUT_DELIVERY_MANAGER_SUCCESS(HttpStatus.OK, "배송 담당자 수정에 성공했습니다."),
//    DELETE_DELIVERY_MANAGER_SUCCESS(HttpStatus.OK, "배송 담당자 삭제에 성공했습니다."),
//    SEARCH_DELIVERY_MANAGER_SUCCESS(HttpStatus.OK, "배송 담당자 검색에 성공했습니다.");

    CREATE_COUPON_SUCCESS(HttpStatus.CREATED, "쿠폰이 생성되었습니다."),
    ISSUE_COUPON_SUCCESS(HttpStatus.OK, "쿠폰이 발급되었습니다."),
    GET_UNEXPIRED_COUPONS_SUCCESS(HttpStatus.OK, "만료되지 않은 쿠폰 목록이 조회되었습니다."),
    GET_COUPON_SUCCESS(HttpStatus.OK, "쿠폰 목록이 조회되었습니다."),
    GET_COUPON_USERS_SUCCESS(HttpStatus.OK, "보유하고 계신 쿠폰 목록이 조회되었습니다."),
    ALREADY_ISSUED_COUPON(HttpStatus.OK, "이미 발급 받은 쿠폰입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}