package com.turkeyjava.security.jwtapp.controller;

import com.turkeyjava.security.jwtapp.config.JwtUtil;
import com.turkeyjava.security.jwtapp.config.MyUserDetailsService;
import com.turkeyjava.security.jwtapp.model.AuthenticateRequest;
import com.turkeyjava.security.jwtapp.model.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloJava {
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public HelloJava(AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping("/hello")
    public ResponseEntity<String> helloJava(){
        return ResponseEntity.ok("hello java");
    }

    @RequestMapping(value = "/authenticate",method = RequestMethod.POST)
    public ResponseEntity<?> responseResponseEntity(@RequestBody AuthenticateRequest request) throws Exception{
        try{
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(),request.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect userName or Password",e);
        }
        final UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUserName());
        final String jwt=jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticateResponse(jwt));
    }
}
