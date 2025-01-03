package com.culture_ticket.client.user.domain.repository;

import com.culture_ticket.client.user.domain.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  Page<User> findAllByIsDeletedFalse(Pageable pageable);
}
