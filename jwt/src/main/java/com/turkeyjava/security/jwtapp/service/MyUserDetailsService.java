package com.turkeyjava.security.jwtapp.service;

import com.turkeyjava.security.jwtapp.model.MyUserDetails;
import com.turkeyjava.security.jwtapp.model.User;
import com.turkeyjava.security.jwtapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("kullanıcı bulunamadı"));
        return new MyUserDetails(user);
    }
}
