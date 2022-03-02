package com.turkeyjava.security.productservice.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
//@PropertySource("classpath:application.properties")
public class StaticKeyAuthorizationKeyFilter implements Filter {

    @Value("${authorization.key}")
    private String authorizationKey;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        var httpRequest=(HttpServletRequest) servletRequest;
        var httpResponse=(HttpServletResponse) servletResponse;

        String authorization=httpRequest.getHeader("Authorization");

        if(authorizationKey.equals(authorization)){
            System.out.println("Success authentication");
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }


    }
}
