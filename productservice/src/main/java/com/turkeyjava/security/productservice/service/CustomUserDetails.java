package com.turkeyjava.security.productservice.service;

import com.turkeyjava.security.productservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user){
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Stream<GrantedAuthority> streamA = user.getRoles().stream()
                .map(e-> new SimpleGrantedAuthority(e.getRoleName()));
        Stream<GrantedAuthority> streamB = user
                .getRoles()
                .stream()
                .flatMap(e->e
                        .getAuthorities()
                        .stream()
                        .map(i -> new SimpleGrantedAuthority(i
                                .getAuthority())));
        return Stream.concat(streamA,streamB).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
