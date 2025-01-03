package com.culture_ticket.client.user.application.dto.request;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class UserInfoUpdateRequestDto {

  private String nickname;
  private String password;
  private String phone;
  private LocalDate birth;

}
