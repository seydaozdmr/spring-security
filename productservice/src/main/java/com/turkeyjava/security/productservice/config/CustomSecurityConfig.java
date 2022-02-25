package com.turkeyjava.security.productservice.config;

import com.turkeyjava.security.productservice.service.CustomAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public CustomSecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser(new User("test","pass",new ArrayList<>()));
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * hasAuthority -> if authenticated user has grantedauthority
         * you can use access for complicate authority managements
         */
        http
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                //.anyRequest()
                //.hasRole("ADMIN")
                //.access("hasAuthority('READ') and !hasAuthority('DELETE')")
                //.hasAuthority("READ")
                //.authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/home",true);

        http.csrf().disable();
        http.headers()
                .frameOptions()
                .sameOrigin();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
}
