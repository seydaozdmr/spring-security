package com.turkeyjava.security.authenticationservice.repository;

import com.turkeyjava.security.authenticationservice.model.Otp;
import com.turkeyjava.security.authenticationservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface OtpRepository extends JpaRepository<Otp, UUID> {
    Optional<Otp> findOtpByUserId(UUID userId);
//    @Query(value = "select p from Otp p join User u on p.user_id=u.id where u.username = ?1")
//    Optional<Otp> findOtpByUserByUserName(String username);
}
