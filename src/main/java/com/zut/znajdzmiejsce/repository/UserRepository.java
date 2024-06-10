package com.zut.znajdzmiejsce.repository;

import com.zut.znajdzmiejsce.model.User;
import com.zut.znajdzmiejsce.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndRole(String email, Role role);

    Optional<User> findByEmailAndRole(String email, Role role);
}
