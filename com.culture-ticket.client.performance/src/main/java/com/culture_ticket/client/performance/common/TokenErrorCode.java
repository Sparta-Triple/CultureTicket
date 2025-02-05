package com.culture_ticket.client.performance.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 * Token의 경우 2000번대 에러코드 사용
 * */
@AllArgsConstructor
@Getter
public enum TokenErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "2000", "Invalid Token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "2001", "Token has expired"),
    TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED.value(), "2002", "Unknown Token Error"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "2003", "Unsupported Token"),
    AUTHORIZATION_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED.value(), "2004",
            "Authorization Token Not Found"),
    NOT_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "2005",
            "Change Refresh Token to Access Token"),
    ;

    // 변형이 일어나면 안되니 모두 final로 선언해 변경되지 않는 값으로 초기화.
    private final Integer httpStatusCode;

    private final String resCode;

    private final String description;

}
