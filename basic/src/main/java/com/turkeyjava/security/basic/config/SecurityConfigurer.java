package com.turkeyjava.security.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
//@Configuration
public class SecurityConfigurer {

    @Bean
    public UserDetailsService myUserDetailsService(){
        UserDetails userDetails=new User("test","pass", List.of(new SimpleGrantedAuthority("read")));
        List<UserDetails> users=List.of(userDetails);
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public PasswordEncoder myPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
