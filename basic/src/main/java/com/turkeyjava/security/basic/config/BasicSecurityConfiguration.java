package com.turkeyjava.security.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;

@EnableWebSecurity
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails seydaOzdemir=User.builder().username("seyda").password("pass").roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(seydaOzdemir);
//    }

    //we use UserDetailsService from bean userDetailsService()
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//      //We can get User Details Service from Authentication Manager Builder
//        auth.inMemoryAuthentication()
//                .getUserDetailsService();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws  Exception {
        UserDetailsService userDetailsService;

        var user = User.withUsername("user")
                .password("pass")
                .authorities("read")
                .build();

        userDetailsService=new InMemoryUserDetailsManager(user);

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


    //we don't use UserDetailsService explicitly
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser(new User("user","pass",new ArrayList<>()));
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
}
