package com.culture_ticket.client.user.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

  // 사용 예시
//  LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
  SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
  LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
  GET_USER_SUCCESS(HttpStatus.OK, "유저 조회에 성공했습니다."),
  UPDATE_USER_SUCCESS(HttpStatus.OK, "유저 정보 수정에 성공했습니다."),
  DELETE_USER_SUCCESS(HttpStatus.OK, "회원 탈퇴에 성공했습니다."),
  SEARCH_USER_SUCCESS(HttpStatus.OK, "회원 검색에 성공했습니다.");

  private final HttpStatus httpStatus;
  private final String message;
}