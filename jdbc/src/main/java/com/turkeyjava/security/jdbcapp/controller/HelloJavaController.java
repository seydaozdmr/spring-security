package com.turkeyjava.security.jdbcapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloJavaController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello turkey java community";
    }
}
