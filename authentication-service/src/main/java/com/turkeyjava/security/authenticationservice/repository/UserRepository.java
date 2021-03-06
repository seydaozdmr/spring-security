package com.turkeyjava.security.authenticationservice.repository;

import com.turkeyjava.security.authenticationservice.model.Otp;
import com.turkeyjava.security.authenticationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserByUsername(String username);
    Optional<Otp> findOtpById(UUID id);
}
