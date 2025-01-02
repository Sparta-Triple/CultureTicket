package com.culture_ticket.client.user.application.service;

import com.culture_ticket.client.user.application.dto.request.SignupRequestDto;
import com.culture_ticket.client.user.domain.model.Role;
import com.culture_ticket.client.user.domain.model.User;
import com.culture_ticket.client.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${admin.code}")
  private String adminCode;

  public void signup(SignupRequestDto signUpRequestDto) {
    Role role = Role.USER;
    if (signUpRequestDto.getRole() == Role.ADMIN && signUpRequestDto.getAdminCode().equals(adminCode)) {
      role = Role.ADMIN;
    }
    User user = User.from(signUpRequestDto.getUsername(),
        signUpRequestDto.getNickname(),
        passwordEncoder.encode(signUpRequestDto.getPassword()),
        signUpRequestDto.getPhone(),
        signUpRequestDto.getBirth(),
        role
    );
    userRepository.save(user);
  }
}
