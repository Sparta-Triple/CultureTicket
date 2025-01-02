package com.culture_ticket.client.user.presentation.controller;

import com.culture_ticket.client.user.application.dto.request.SignupRequestDto;
import com.culture_ticket.client.user.application.service.UserService;
import com.culture_ticket.client.user.common.ResponseMessageDto;
import com.culture_ticket.client.user.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseMessageDto signup(@RequestBody SignupRequestDto signupRequestDto) {
    userService.signup(signupRequestDto);
    return new ResponseMessageDto(ResponseStatus.SIGNUP_SUCCESS);
  }

//  테스트용 API
//  @GetMapping("/test")
//  public String test() {
//    return "test success";
//  }
}
