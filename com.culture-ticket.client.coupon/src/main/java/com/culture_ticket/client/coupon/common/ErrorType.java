package com.culture_ticket.client.coupon.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    // Category
    CATEGORY_DUPLICATE(HttpStatus.CONFLICT, "카테고리가 이미 존재합니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // Performance
    PERFORMANCE_DUPLICATE(HttpStatus.CONFLICT, "공연이 이미 존재합니다."),
    PERFORMANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "공연을 찾을 수 없습니다."),

    // TimeTable

    // Role
    ROLE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    TIMETABLE_NOT_FOUND(HttpStatus.NOT_FOUND, "타임테이블을 찾을 수 없습니다."),

    // seat
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "좌석을 찾을 수 없습니다.");



    private final HttpStatus httpStatus;
    private final String message;
}