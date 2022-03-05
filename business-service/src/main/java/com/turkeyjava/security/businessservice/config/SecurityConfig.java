package com.turkeyjava.security.businessservice.config;

import com.turkeyjava.security.businessservice.filter.InitialAuthenticationFilter;
import com.turkeyjava.security.businessservice.filter.JwtAuthenticationFilter;
import com.turkeyjava.security.businessservice.service.OtpAuthenticationProvider;
import com.turkeyjava.security.businessservice.service.UsernamePasswordAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final InitialAuthenticationFilter initialAuthenticationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OtpAuthenticationProvider otpAuthenticationProvider;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    public SecurityConfig(InitialAuthenticationFilter initialAuthenticationFilter,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          OtpAuthenticationProvider otpAuthenticationProvider,
                          UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider) {
        this.initialAuthenticationFilter = initialAuthenticationFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.otpAuthenticationProvider = otpAuthenticationProvider;
        this.usernamePasswordAuthenticationProvider = usernamePasswordAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
                .authenticationProvider(otpAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter,BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().authenticated();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
