package com.turkeyjava.security.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Set your configuration on the auth object. Custom Authentication Manager
//        auth.inMemoryAuthentication()
//                .withUser("turkeyjava")
//                .password("community")
//                .roles("USER")
//        .and()
//        .withUser("test")
//        .password("admin")
//        .roles("ADMIN")
//        .and()
//        .withUser("herkes")
//        .password("herkes")
//        .roles("HERKES");
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(User.withUsername("admin").password("test").roles("ADMIN"))
                .withUser(User.withUsername("user").password("test").roles("USER"));
    }


    @Bean
    public PasswordEncoder encoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/").permitAll() // static içeriği herkes görebilsin diye
//                .antMatchers("/admin/**").hasRole("ADMIN") //most restrictive to least restrictive
//                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
//                .antMatchers("/hello/**").hasAnyRole("HERKES","USER")
//                .anyRequest().authenticated() //yetkilendirilmiş herkes bütün pathlara girebilir
//        .and().formLogin();
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN","USER")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/**").permitAll()
                .and().formLogin();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
