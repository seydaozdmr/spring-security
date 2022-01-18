package com.turkeyjava.security.basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @RequestMapping("/hello")
    public String helloWorld(){
        return "hello world";
    }

    @RequestMapping("/user")
    public String helloUser(){
        return "hello user";
    }

    @RequestMapping("/admin")
    public String helloAdmin(){
        return "hello admin";
    }
}
