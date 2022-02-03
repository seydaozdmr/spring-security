package com.turkeyjava.security.jwtapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloJava {

    @RequestMapping("/hello")
    public ResponseEntity<String> helloJava(){
        return ResponseEntity.ok("hello java");
    }
}
