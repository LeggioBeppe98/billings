package com.bldev.billings.utils;

import com.bldev.billings.model.Role;
import com.bldev.billings.model.User;
import com.bldev.billings.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (!userRepository.existsByEmail("admin@timescan.com")) {
            User admin = new User();
            admin.setNome("Admin");
            admin.setCognome("Timescan");
            admin.setEmail("admin@timescan.com");
            admin.setRole(Role.ROLE_ADMIN);
            admin.setPasswordHash(passwordEncoder.encode("password"));
            admin.setAttivo(true);

            userRepository.save(admin);
            System.out.println("Admin creato automaticamente");
        }
    }
}