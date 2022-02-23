package com.turkeyjava.security.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
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
    @Autowired
    private AuthenticationProvider provider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

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

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws  Exception {
//        /**
//         * SecurityConfigurer sıfınıda kurulan yapı ile aşağıdaki yapı aynıdır
//         * UserDetailsService yaratılıyor, concrete olarak implemente edilmiş InMemoryUserDetailsManager
//         * sınıfının constructor'ına oluşturulan UserDetails "user" nesnesi veriliyor.
//         * Daha sonra auth nesnesine (AuthenticationManagerBuilder)
//         */
//        UserDetailsService userDetailsService;
//
//        var user = User.withUsername("user")
//                .password("pass")
//                .authorities("read")
//                .build();
//
//        userDetailsService=new InMemoryUserDetailsManager(user);
//        /**
//         * CustomAuthenticationProvider buraya eklenir. (AuthenticationProvider)
//         */
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
//    }


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
//        http.httpBasic(c -> {
//            c.realmName("OTHER");
//            c.authenticationEntryPoint(new CustomEntryPoint());
//        });
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
}
