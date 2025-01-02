package com.culture_ticket.client.user.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

  private String username;
  private String password;

}
