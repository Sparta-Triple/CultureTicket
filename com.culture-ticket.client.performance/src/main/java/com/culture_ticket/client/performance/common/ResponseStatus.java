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
    GET_PERFORMANCE_SUCCESS(HttpStatus.OK, "공연 조회에 성공했습니다."),
    UPDATE_PERFORMANCE_STATUS_SUCCESS(HttpStatus.OK, "공연 상태 수정에 성공했습니다."),
    UPDATE_PERFORMANCE(HttpStatus.OK, "공연 수정에 성공했습니다."),
    DELETE_PERFORMANCE_SUCCESS(HttpStatus.OK, "공연 삭제에 성공했습니다."),

    // timetable
    CREATE_TIME_TABLE_SUCCESS(HttpStatus.OK, "타임테이블 생성에 성공하였습니다."),
    SEARCH_TIME_TABLE_SUCCESS(HttpStatus.OK, "타임테이블 검색에 성공했습니다."),
    UPDATE_TIMETABLE_STATUS_SUCCESS(HttpStatus.OK, "타임테이블 상태 변경에 성공했습니다."),
    DELETE_TIMETABLE_SUCCESS(HttpStatus.OK, "타임테이블 삭제에 성공했습니다."),
    RESTORE_TIMETABLE_SUCCESS(HttpStatus.OK, "타임테이블 복원에 성공했습니다."),

    // Seat
    CREATE_SEAT_SUCCESS(HttpStatus.CREATED,"좌석 생성에 성공했습니다."),
    GET_SEAT_INFO_SUCCESS(HttpStatus.OK, "좌석 목록 조회에 성공했습니다."),
    UPDATE_SEAT_STATUS_SUCCESS(HttpStatus.OK, "좌석 상태 변경에 성공했습니다."),
    DELETE_SEAT_SUCCESS(HttpStatus.OK,"좌석 삭제에 성공했습니다."),
    RESTORE_SEAT_SUCCESS(HttpStatus.OK, "좌석 복구에 성공했습니다."),
    UPDATE_SEAT_PRICE_SUCCESS(HttpStatus.OK,"좌석 가격 변경에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}