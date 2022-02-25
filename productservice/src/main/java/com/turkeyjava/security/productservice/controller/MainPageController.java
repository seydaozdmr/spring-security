package com.turkeyjava.security.productservice.controller;

import com.turkeyjava.security.productservice.model.Product;

import com.turkeyjava.security.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainPageController {
    private final ProductService productService;

    public MainPageController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/home")
    public ResponseEntity<?> home(){
        List<Product> products=productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @RequestMapping("/user")
    public ResponseEntity<String> user(Authentication authentication){
        return ResponseEntity.ok(authentication.getName());
    }

    @RequestMapping("/admin")
    public String admin(){
        return "this is admin paga";
    }

    @RequestMapping("/guess")
    public String guess(){
        return "wellcome guess";
    }
}
