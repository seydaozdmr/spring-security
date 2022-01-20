package com.turkeyjava.security.basic.service;

import com.turkeyjava.security.basic.model.MyUserDetails;
import com.turkeyjava.security.basic.model.User;
import com.turkeyjava.security.basic.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(()->new RuntimeException("kullanıcı bulunamadı"));
        return new MyUserDetails(user);
    }
}
