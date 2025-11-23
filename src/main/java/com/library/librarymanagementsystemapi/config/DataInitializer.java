package com.library.librarymanagementsystemapi.config;

import com.library.librarymanagementsystemapi.entity.User;
import com.library.librarymanagementsystemapi.enums.Role;
import com.library.librarymanagementsystemapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if admin already exists
            List<User> admins = userRepository.findByRole(Role.ADMIN);

            if (admins.isEmpty()) {
                // Create default admin user
                User admin = new User();
                admin.setEmail("admin@library.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFirstName("Admin");
                admin.setLastName("User");
                admin.setRole(Role.ADMIN);
                admin.setIsActive(true);

                userRepository.save(admin);

                System.out.println("========================================");
                System.out.println("✅ Default Admin User Created");
                System.out.println("========================================");
                System.out.println("Email:    admin@library.com");
                System.out.println("Password: admin123");
                System.out.println("========================================");
            }

            // Optionally create a demo member user
            if (userRepository.findByEmail("member@library.com").isEmpty()) {
                User member = new User();
                member.setEmail("member@library.com");
                member.setPassword(passwordEncoder.encode("member123"));
                member.setFirstName("John");
                member.setLastName("Doe");
                member.setRole(Role.MEMBER);
                member.setIsActive(true);

                userRepository.save(member);

                System.out.println("✅ Default Member User Created");
                System.out.println("Email:    member@library.com");
                System.out.println("Password: member123");
                System.out.println("========================================");
            }
        };
    }
}