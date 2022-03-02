package com.turkeyjava.security.productservice.filter;

import org.springframework.security.web.csrf.CsrfToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class CsrfTokenLogger implements Filter {
    private Logger logger= Logger.getLogger(CsrfTokenLogger.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        var request=(HttpServletRequest) servletRequest;
        var response=(HttpServletResponse) servletResponse;

        CsrfToken requestToken=(CsrfToken) request.getAttribute("_csrf");
        String responseToken=response.getHeader("_csrf");
        logger.info(requestToken.getToken());
        //logger.info("response : "+ responseToken);
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
