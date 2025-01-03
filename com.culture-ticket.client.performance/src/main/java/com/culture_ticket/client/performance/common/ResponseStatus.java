package com.culture_ticket.client.performance.common;

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

    // category
    CREATE_CATEGORY_SUCCESS(HttpStatus.CREATED, "카테고리 생성에 성공했습니다."),
    GET_CATEGORY_SUCCESS(HttpStatus.OK, "카테고리 조회에 성공했습니다."),
    UPDATE_CATEGORY_SUCCESS(HttpStatus.OK, "카테고리 수정에 성공했습니다."),
    DELETE_CATEGORY_SUCCESS(HttpStatus.OK, "카테고리 삭제에 성공했습니다."),

    // performance
    CREATE_PERFORMANCE_SUCCESS(HttpStatus.CREATED, "공연 생성에 성공했습니다."),
    GET_PERFORMANCE_SUCCESS(HttpStatus.OK, "공연 조회에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}