package com.turkeyjava.security.productservice.config;

import com.turkeyjava.security.productservice.model.*;
import com.turkeyjava.security.productservice.repository.ProductRepository;
import com.turkeyjava.security.productservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    public LoadData(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Authority authority=new Authority();
        authority.setAuthority("READ");

        Role role=new Role();
        role.setRoleName("ADMIN");
        role.setAuthorities(List.of(authority));
        authority.setRoles(List.of(role));

        User user=new User();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setActive(true);
        user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
        user.setRoles(List.of(role));
        role.setUsers(List.of(user));

        userRepository.save(user);

        Product p1=new Product();
        p1.setProductName("Book");
        p1.setPrice(100);
        p1.setCurrency(Currency.EUR);
        productRepository.save(p1);
    }
}
