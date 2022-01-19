package com.turkeyjava.security.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Set your configuration on the auth object. Custom Authentication Manager
        auth.inMemoryAuthentication()
                .withUser("turkeyjava")
                .password("community")
                .roles("USER")
        .and()
        .withUser("test")
        .password("admin")
        .roles("ADMIN")
        .and()
        .withUser("herkes")
        .password("herkes")
        .roles("HERKES");
    }

    @Bean
    public PasswordEncoder encoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll() // static içeriği herkes görebilsin diye
                .antMatchers("/admin/**").hasRole("ADMIN") //most restrictive to least restrictive
                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/hello/**").hasAnyRole("HERKES","USER")
                .anyRequest().authenticated() //yetkilendirilmiş herkes bütün pathlara girebilir
        .and().formLogin();
    }
}
