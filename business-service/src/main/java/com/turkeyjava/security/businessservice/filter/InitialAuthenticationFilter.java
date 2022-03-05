package com.turkeyjava.security.businessservice.filter;

import com.turkeyjava.security.businessservice.auth.OtpAuthentication;
import com.turkeyjava.security.businessservice.auth.UserNamePasswordAuthentication;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager manager;
    @Value("${jwt.signing.key}")
    private String signingKey;

    public InitialAuthenticationFilter(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String username=request.getHeader("username");
        String password=request.getHeader("password");
        String code=request.getHeader("code");
        /**
         * Eğer gelen isteğin header'ında code yoksa authentication için
         * UsernamePasswordAuthentication sınıfını kullanırız, dolaysıyla UsernamePasswordAuthenticationProvider çalışır
         * çünkü support() methodu UsernamePasswordAuthentication gelirse çalışacak şekilde ayarlandı.
         */

        if(code==null){
            Authentication a=new UserNamePasswordAuthentication(username,password);
            manager.authenticate(a);
        }else {
            Authentication a=new OtpAuthentication(username,code);
            SecretKey key = Keys.hmacShaKeyFor(
                    signingKey.getBytes(
                            StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .setClaims(Map.of("username",username))
                    .signWith(key)
                    .compact();

            response.setHeader("Authorization",jwt);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        /**
         * yalnızca /login path'ina izin veriyor.
         */
        return !request.getServletPath().equals("/login");
    }
}
