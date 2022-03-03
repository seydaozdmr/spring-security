package com.turkeyjava.security.productservice.repository;

import com.turkeyjava.security.productservice.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaTokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findTokenByIdentifier(String identifier);
}
