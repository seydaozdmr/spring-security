package com.turkeyjava.security.productservice.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.turkeyjava.security.productservice.model.EncryptionAlgorithm.BCRYPT;
import static com.turkeyjava.security.productservice.model.EncryptionAlgorithm.SCRYPT;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final JpaUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    public CustomAuthenticationProvider(JpaUserDetailsService userDetailsService,
                                        BCryptPasswordEncoder bCryptPasswordEncoder,
                                        SCryptPasswordEncoder sCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sCryptPasswordEncoder = sCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password=authentication.getCredentials().toString();

        CustomUserDetails userDetails= userDetailsService.loadUserByUsername(username);
        switch (userDetails.getUser().getAlgorithm()) {
            case BCRYPT:
                return checkPassword(userDetails, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(userDetails, password, sCryptPasswordEncoder);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication checkPassword(CustomUserDetails user,
                                         String rawPassword,
                                         PasswordEncoder encoder) {

        if (encoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
