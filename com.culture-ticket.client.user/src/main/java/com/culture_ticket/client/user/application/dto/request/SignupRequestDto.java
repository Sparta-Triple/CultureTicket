package com.culture_ticket.client.user.application.dto.request;

import com.culture_ticket.client.user.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

  @Email(message = "유효하지 않은 이메일 양식입니다.")
  private String username;

  private String nickname;

  @Pattern(
      regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$])[A-Za-z\\d@$!%*#?&]{8,15}$",
      message = "비밀번호는 8~15자의 알파벳 대소문자, 숫자, 특수문자를 포함해야 합니다."
  )
  private String password;

  @Pattern(
      regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다."
  )
  private String phone;

  @DateTimeFormat
  private LocalDate birth;

  @Pattern(
      regexp = "ADMIN|USER"
  )
  private Role role;

  private String adminCode;
}
