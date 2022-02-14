package com.turkeyjava.security.form.service;

import com.turkeyjava.security.form.model.MyUserDetails;
import com.turkeyjava.security.form.model.User;
import com.turkeyjava.security.form.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
