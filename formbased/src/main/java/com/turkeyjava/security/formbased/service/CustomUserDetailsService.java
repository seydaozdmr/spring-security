package com.turkeyjava.security.formbased.service;

import com.turkeyjava.security.formbased.model.CustomUserDetails;
import com.turkeyjava.security.formbased.model.User;
import com.turkeyjava.security.formbased.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(()->new RuntimeException("kullanıcı bulunamadı"));
        return new CustomUserDetails(user);
    }
}
