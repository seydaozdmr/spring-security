package com.turkeyjava.security.productservice.repository;

import com.turkeyjava.security.productservice.model.Token;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Customizer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomCsrfTokenRepository implements CsrfTokenRepository {
    private final JpaTokenRepository jpaTokenRepository;

    public CustomCsrfTokenRepository(JpaTokenRepository jpaTokenRepository) {
        this.jpaTokenRepository = jpaTokenRepository;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String uuid= UUID.randomUUID().toString();
        return new DefaultCsrfToken("X-CSRF-TOKEN","_csrf",uuid);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        String identifier =
                request.getHeader("Cookie");
        identifier=identifier.substring(identifier.indexOf("="));
        List<? super IOException> exceptions=new ArrayList<Exception>();


        Optional<Token> existingToken = jpaTokenRepository
                                            .findTokenByIdentifier(identifier);
        System.out.println("identifier "+ identifier);
        if (existingToken.isPresent()) {
            System.out.println("db token :"+ existingToken.get().getToken());
            Token csrfToken = existingToken.get();
            csrfToken.setToken(token.getToken());
        } else {
            Token csrfToken = new Token();
            csrfToken.setToken(token.getToken());
            csrfToken.setIdentifier(identifier);
            jpaTokenRepository.save(csrfToken);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String identifier = request.getHeader("Cookie");
        identifier=identifier.substring(identifier.indexOf("="));
        Optional<Token> token=jpaTokenRepository
                                .findTokenByIdentifier(identifier);
        if(token.isPresent()){
            Token csrfToken=token.get();
            return new DefaultCsrfToken(
                    "X-CSRF-TOKEN",
                    "_csrf",
                    csrfToken.getToken());
        }
        return null;
    }
}
