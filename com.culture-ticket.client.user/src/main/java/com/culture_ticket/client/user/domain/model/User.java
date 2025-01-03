package com.culture_ticket.client.user.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_user")
@Builder
public class User extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false, nullable = false)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, length = 20, unique = true)
  private String nickname;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String phone;

  @Column(nullable = false)
  private LocalDate birth;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  public static User from(String username, String nickname, String password, String phone,
      LocalDate birth, Role role) {
    User user = User.builder()
        .username(username)
        .nickname(nickname)
        .password(password)
        .phone(phone)
        .birth(birth)
        .role(role).build();
    user.createdBy(username);
    return user;
  }

  public void update(String nickname, String password, String phone, LocalDate birth, String requestUserName) {
    this.nickname = Objects.nonNull(nickname) ?  nickname : this.nickname;
    this.password = Objects.nonNull(password) ?  password : this.password;
    this.phone = Objects.nonNull(phone) ?  phone : this.phone;
    this.birth = Objects.nonNull(birth) ?  birth : this.birth;
    this.updatedBy(requestUserName);
  }

  public void deletedBy(String username) {
    this.softDeletedBy(username);
  }
}