package com.culture_ticket.client.user.application.service;

import com.culture_ticket.client.user.application.dto.request.SignupRequestDto;
import com.culture_ticket.client.user.application.dto.request.UserInfoUpdateRequestDto;
import com.culture_ticket.client.user.application.dto.response.UserInfoResponseDto;
import com.culture_ticket.client.user.common.CustomException;
import com.culture_ticket.client.user.common.ErrorType;
import com.culture_ticket.client.user.domain.model.Role;
import com.culture_ticket.client.user.domain.model.User;
import com.culture_ticket.client.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<UserInfoResponseDto> getUserInfos(Pageable pageable) {
    Page<User> usersPage = userRepository.findAllByIsDeletedFalse(pageable);
    return usersPage.map(UserInfoResponseDto::new);
  }

  public UserInfoResponseDto getUserInfo(Long userId) {
    User user = findUserById(userId);
    return new UserInfoResponseDto(user);
  }

  public void updateUserInfo(Long userId, Long requestUserId, UserInfoUpdateRequestDto requestDto) {
    User user = findUserById(userId);
    User requestUser = findUserById(requestUserId);
    user.updated(requestDto.getNickname(),
        passwordEncoder.encode(requestDto.getPassword()),
        requestDto.getPhone(),
        requestDto.getBirth(),
        requestUser.getUsername()
    );
  }

  public void deleteUser(Long userId, Long requestUserId, Role role) {
    User user = findUserById(userId);
    User requestUser = findUserById(requestUserId);
    if (!role.equals(Role.ADMIN) || !userId.equals(requestUserId)) {
      throw new CustomException(ErrorType.ACCESS_DENIED);
    }
    user.deleted(requestUser.getUsername());
  }

  public void restoreUser(Long userId, Long requestUserId, Role role) {
    User user = findUserById(userId);
    User requestUser = findUserById(requestUserId);
    if (!role.equals(Role.ADMIN) || !userId.equals(requestUserId)) {
      throw new CustomException(ErrorType.ACCESS_DENIED);
    }
    user.restored(requestUser.getUsername());
  }

  private User findUserById(Long userId){
    return userRepository.findById(userId).orElseThrow(
        () -> new CustomException(ErrorType.NOT_FOUND_USER)
    );
  }

}
