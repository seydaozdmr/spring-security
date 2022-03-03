package com.turkeyjava.security.productservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @PostMapping("/product/add")
    public String productAdd(@RequestParam String name){
        System.out.println("Name : "+name);
        return "main";
    }

    @PostMapping("/product/update")
    public String productUpdate(@RequestParam String name){
        System.out.println("Name : "+name);
        return "main";
    }
}
