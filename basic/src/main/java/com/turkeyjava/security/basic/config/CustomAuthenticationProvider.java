package com.turkeyjava.security.basic.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Bu sınıf Authentication işlemini gerçekleştiren provider sınıfıdır.
 * Kendi custom provider'ımızı yazarak kendi kimlik doğrulama işlemimizi gerçekleştirebiliriz.
 * We mark the class with @Component to have an instance of its type in the context managed by Spring.
 * Then, we have to decide what kind of Authentication interface implementation this
 * AuthenticationProvider supports.
 * That depends on what type we expect to be provided as a parameter to the authenticate() method.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *Kimlik doğrulamanın gerçekleştiği yer.
     * We make use of the UserDetailsService implementation to get the UserDetails.
     * If the user doesn’t exist, the loadUserByUsername() method should throw an AuthenticationException.
     * In this case, the authentication process stops, and the HTTP filter sets the response status to
     * HTTP 401 Unauthorized. If the username exists, we can check further the user’s password with the
     * matches() method of the PasswordEncoder from the context. If the password does not match, then again,
     * an AuthenticationException should be thrown. If the password is correct, the AuthenticationProvider
     * returns an instance of Authentication marked as “authenticated,” which contains the details about
     * the request.
     * AuthenticationManager bu işlemleri provider'a delege eder. Bu sırada authentication nesnesini,
     * UserDetailsService'i ve PasswordEncoder'ı da gönderir. Kişinin kimlik doğrulama için gerekli bilgileri
     * authentication nesnesinde tutulur. Burada ise gelen bilgilerle UserDetailsService'den çekilen kullanıcı
     * bilgileri PasswordEncoder kullanılarak karşılaştırılır.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password=authentication.getCredentials().toString();

        UserDetails userDetails=userDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password,userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(username,password,userDetails.getAuthorities());
        }else{
            throw new BadCredentialsException("Kullanıcı adı ya da şifre yanlış.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
