package com.turkeyjava.security.jdbcapp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class JdbcAppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService myUserDetailsService(DataSource dataSource){
        var userDetailsService= new JdbcUserDetailsManager(dataSource);
        /**
         * bazen ufak bir detay için saatler ya da günler harcayabilirsin
         */
        userDetailsService.setUsersByUsernameQuery("select username,password,enabled from spring.users where username = ?");
        userDetailsService.setAuthoritiesByUsernameQuery("select username,authority from spring.authorities where username = ?");

        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * Bu implementasyon; myUserDetailsService bean'ini auth'a userDetailsService olarak vererek
         * ya da direkt auth.jdbcAuthentication() methodu ile gerekli alanları doldurarak
         * Authentication'a UserDetailsService verilmesi işlemi gerçekleştirilebilir.
         */
        auth//.userDetailsService(myUserDetailsService(dataSource));
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username,password,enabled from spring.users where username = ?")
                .authoritiesByUsernameQuery("select username,authority from spring.authorities where username = ?");;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        //.formLogin();
        //http.csrf().disable();
        http.csrf()
                .ignoringAntMatchers("/h2-console/**");
        http.headers()
                .frameOptions()
                .sameOrigin();
        //http.headers().frameOptions().disable();
    }
}
