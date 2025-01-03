package com.culture_ticket.client.user.application.dto.response;

import com.culture_ticket.client.user.domain.model.Role;
import com.culture_ticket.client.user.domain.model.User;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {
  private Long id;
  private String username;
  private String nickname;
  private String phone;
  private LocalDate birth;
  private Role role;

  public UserInfoResponseDto (User user){
    this.id = user.getId();
    this.username = user.getUsername();
    this.nickname = user.getNickname();
    this.phone = user.getPhone();
    this.birth = user.getBirth();
    this.role = user.getRole();
  }
}
