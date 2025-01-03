package com.culture_ticket.client.user.presentation.controller;

import com.culture_ticket.client.user.application.dto.request.SignupRequestDto;
import com.culture_ticket.client.user.application.dto.request.UserInfoUpdateRequestDto;
import com.culture_ticket.client.user.application.dto.response.UserInfoResponseDto;
import com.culture_ticket.client.user.application.service.UserService;
import com.culture_ticket.client.user.common.ResponseDataDto;
import com.culture_ticket.client.user.common.ResponseMessageDto;
import com.culture_ticket.client.user.common.ResponseStatus;
import com.culture_ticket.client.user.domain.model.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  // 회원 가입
  @PostMapping("/signup")
  public ResponseMessageDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
    userService.signup(signupRequestDto);
    return new ResponseMessageDto(ResponseStatus.SIGNUP_SUCCESS);
  }

  // 사용자 정보 목록 조회
  @Secured("ROLE_ADMIN")
  @GetMapping("/info")
  public ResponseDataDto<Page<UserInfoResponseDto>> getUserInfos(
      @PageableDefault Pageable pageable) {
    Page<UserInfoResponseDto> userInfos = userService.getUserInfos(pageable);
    return new ResponseDataDto<>(ResponseStatus.GET_USER_SUCCESS, userInfos);
  }

  // 사용자 정보 단일 조회
  @Secured("ROLE_ADMIN")
  @GetMapping("/info/{userId}")
  public ResponseDataDto<UserInfoResponseDto> getUserInfo(@PathVariable Long userId) {
    UserInfoResponseDto userInfo = userService.getUserInfo(userId);
    return new ResponseDataDto<>(ResponseStatus.GET_USER_SUCCESS, userInfo);
  }

  // 사용자 정보 수정
  @PatchMapping("/info/{userId}")
  public ResponseMessageDto updateUserInfo(
      @RequestHeader(value = "X-User-Id") Long requestUserId,
      @PathVariable Long userId,
      @RequestBody UserInfoUpdateRequestDto requestDto) {
    userService.updateUserInfo(userId, requestUserId, requestDto);
    return new ResponseMessageDto(ResponseStatus.UPDATE_USER_SUCCESS);
  }

  // 회원 탈퇴
  @DeleteMapping("/{userId}")
  public ResponseMessageDto deleteUser(
      @PathVariable Long userId,
      @RequestHeader(value = "X-User-Id") Long requestUserId,
      @RequestHeader(value = "X-User-Role") Role role) {
    userService.deleteUser(userId, requestUserId, role);
    return new ResponseMessageDto(ResponseStatus.DELETE_USER_SUCCESS);
  }

  // 회원 복구
  @PatchMapping("/{userId}")
  public ResponseMessageDto restoreUser(
      @PathVariable Long userId,
      @RequestHeader(value = "X-User-Id") Long requestUserId,
      @RequestHeader(value = "X-User-Role") Role role) {
    userService.restoreUser(userId, requestUserId, role);
    return new ResponseMessageDto(ResponseStatus.DELETE_USER_SUCCESS);
  }

//  테스트용 API
//  @GetMapping("/test")
//  public String test() {
//    return "test success";
//  }
}
