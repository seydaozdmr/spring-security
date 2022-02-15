package com.turkeyjava.security.formbased.config;

import com.turkeyjava.security.formbased.model.User;
import com.turkeyjava.security.formbased.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadData implements CommandLineRunner {
    private final UserRepository userRepository;

    public LoadData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user=new User();
        user.setUserName("test");
        user.setPassword("user");
        user.setActive(true);
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        User user2=new User();
        user2.setUserName("admin");
        user2.setPassword("test");
        user2.setActive(true);
        user2.setRoles("ROLE_ADMIN");
        userRepository.save(user2);
    }
}
