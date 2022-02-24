package com.turkeyjava.security.productservice.service;

import com.turkeyjava.security.productservice.model.User;
import com.turkeyjava.security.productservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository
                .findUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Kullanıcı adı ya da parola yanlış"));

        return new CustomUserDetails(user);
    }
}
