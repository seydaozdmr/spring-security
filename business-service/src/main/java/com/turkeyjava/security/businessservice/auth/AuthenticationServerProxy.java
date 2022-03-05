package com.turkeyjava.security.businessservice.auth;

import com.turkeyjava.security.businessservice.model.Otp;
import com.turkeyjava.security.businessservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationServerProxy {
    private final RestTemplate restTemplate;

    public AuthenticationServerProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${auth.server.base.url}")
    private String baseUrl;

    public void sendAuth(String username,String password){
        String url=baseUrl+"/users/auth";

        var body=new User();
        body.setUsername(username);
        body.setPassword(password);

        var request=new HttpEntity<>(body);

        restTemplate.postForEntity(url,request,Void.class);
    }

    public boolean sendOTP(String username,String code){
        String url = baseUrl + "/otp/check";
        var body=new Otp();
        var user=new User();
        user.setUsername(username);
        body.setCode(code);
        body.setUser(user);
        var request=new HttpEntity<>(body);
        var response=restTemplate.postForEntity(url,request,Void.class);

        return response.getStatusCode().equals(HttpStatus.OK);
    }


}
