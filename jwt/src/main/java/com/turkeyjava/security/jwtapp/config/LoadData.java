package com.turkeyjava.security.jwtapp.config;


import com.turkeyjava.security.jwtapp.model.User;
import com.turkeyjava.security.jwtapp.repository.UserRepository;
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
        System.out.println("kullanıcı yüklendi");
    }
}
