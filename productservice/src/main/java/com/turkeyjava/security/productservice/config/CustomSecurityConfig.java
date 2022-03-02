package com.turkeyjava.security.productservice.config;

import com.turkeyjava.security.productservice.filter.AuthenticationLoggingFilter;
import com.turkeyjava.security.productservice.filter.RequestValidationFilter;
import com.turkeyjava.security.productservice.filter.StaticKeyAuthorizationKeyFilter;
import com.turkeyjava.security.productservice.service.CustomAuthenticationProvider;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomAuthenticationProvider customAuthenticationProvider;
    /**
     * Bu filtrenin application.properties dosyasındaki veriyi alabilmesi için bean olarak yaratılması
     * ve run time'de kullanılması gerekiyor.
     */
    private final StaticKeyAuthorizationKeyFilter filter;
    public CustomSecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, StaticKeyAuthorizationKeyFilter filter) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.filter = filter;
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
                .anyRequest().permitAll();
                //.antMatchers(HttpMethod.POST,"/admin").hasRole("ADMIN")
                /**
                 * IMPORTANT To say it again: I recommend and prefer MVC matchers.
                 * Using MVC matchers, you avoid some of the risks involved with
                 * the way Spring maps paths to actions. This is because you know
                 * that the way paths are interpreted for the authorization rules
                 * are the same as Spring itself interprets these for mapping the
                 * paths to endpoints. When you use Ant matchers, exercise caution
                 * and make sure your expressions indeed match everything for which
                 * you need to apply authorization rules.
                 */
                //.antMatchers("/admin").hasRole("ADMIN")
                //.antMatchers("/user").hasRole("USER")
                //.anyRequest().authenticated();

                //.anyRequest()
                //.hasRole("ADMIN")
                //.access("hasAuthority('READ') and !hasAuthority('DELETE')")
                //.hasAuthority("READ")
                //.authenticated()
                //.and()
                //.formLogin()
                //.defaultSuccessUrl("/home",true)
        //.and().httpBasic();

        http.csrf().disable();
        http.headers()
                .frameOptions()
                .sameOrigin();
        /**
         * Configuring the custom filter before authentication
         */
        http.addFilterBefore(new RequestValidationFilter(),
                BasicAuthenticationFilter.class);
        /**
         * Configuring the custom filter after authentication
         */
        http.addFilterAfter(new AuthenticationLoggingFilter(),
                BasicAuthenticationFilter.class);
        /**
         * Configuring the custom filter instead basic authentication filter
         */
        http.addFilterAt(filter,
                BasicAuthenticationFilter.class);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }
}
