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
    /**
     * Spring framework AuthenticationManagerBuilder'a UserDetailsService'i inject ediyor
     * public <T extends UserDetailsService> DaoAuthenticationConfigurer<AuthenticationManagerBuilder, T>
     *     userDetailsService(T userDetailsService) throws Exception
     *     Bu method'a inject ediliyor.
     * @return
     */

    @Bean
    public UserDetailsService myUserDetailsService(){
        UserDetails userDetails=new User("test","pass", List.of(new SimpleGrantedAuthority("read")));
        List<UserDetails> users=List.of(userDetails);
        /**
         * InMemoryUserDetails UserDetailsManager interface'inin implementasyonu, ki o da UserDetailsService interface'ini extend eden
         * bir interface, Bu sınafın constructor'ına verdiğimiz UserDetails kendi içersindeki bir map'de saklanır.
         * UserDetailsService'in loadUserByUsername methodu'da bu sınıf içinde gerçekleştirilmiştir. Dolaysıyla kullanıcı kimlik
         * bilgileri çekileceği zaman bu sınıf içindeki map'den bu bilgiler çekilir.
         */
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public PasswordEncoder myPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
