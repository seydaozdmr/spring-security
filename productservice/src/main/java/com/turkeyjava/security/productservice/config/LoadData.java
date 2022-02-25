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
        role.setRoleName("ROLE_ADMIN");
        role.addAuthority(authority);
        authority.addRole(role);

        User user=new User();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("pass"));
        user.setActive(true);
        user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
        user.setRoles(List.of(role));
        role.addUser(user);
        userRepository.save(user);

        Authority authority1=new Authority();
        authority1.setAuthority("READ");

        Role role1=new Role();
        role1.setRoleName("ROLE_USER");
        role1.addAuthority(authority1);
        authority1.addRole(role1);

        User user1=new User();
        user1.setUsername("user");
        user1.setPassword(passwordEncoder.encode("pass"));
        user1.setActive(true);
        user1.setAlgorithm(EncryptionAlgorithm.BCRYPT);
        user1.setRoles(List.of(role1));
        role1.addUser(user1);
        userRepository.save(user1);


        Product p1=new Product();
        p1.setProductName("Book");
        p1.setPrice(100);
        p1.setCurrency(Currency.EUR);
        productRepository.save(p1);
    }
}
