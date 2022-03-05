package com.turkeyjava.security.businessservice.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserNamePasswordAuthentication extends UsernamePasswordAuthenticationToken {
    public UserNamePasswordAuthentication(Object principal,
                                          Object credentials,
                                          Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public UserNamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }



}
