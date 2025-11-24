package com.library.librarymanagementsystemapi.config;

import com.library.librarymanagementsystemapi.entity.Member;
import com.library.librarymanagementsystemapi.entity.User;
import com.library.librarymanagementsystemapi.enums.Role;
import com.library.librarymanagementsystemapi.repository.MemberRepository;
import com.library.librarymanagementsystemapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   MemberRepository memberRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // Create admin user
            if (userRepository.findByRole(Role.ADMIN).isEmpty()) {
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

            // Create member user AND corresponding member record
            if (userRepository.findByEmail("member@library.com").isEmpty()) {
                // Create User
                User memberUser = new User();
                memberUser.setEmail("member@library.com");
                memberUser.setPassword(passwordEncoder.encode("member123"));
                memberUser.setFirstName("John");
                memberUser.setLastName("Doe");
                memberUser.setRole(Role.MEMBER);
                memberUser.setIsActive(true);

                User savedUser = userRepository.save(memberUser);

                // Create corresponding Member record and LINK to user
                Member member = new Member();
                member.setUser(savedUser);  // ⭐ IMPORTANT: Link to user
                member.setFirstName("John");
                member.setLastName("Doe");
                member.setEmail("member@library.com");
                member.setPhoneNumber("5551234567");
                member.setAddress("123 Library Street");
                member.setIsActive(true);
                // membershipDate and expiryDate will be set by @PrePersist

                Member savedMember = memberRepository.save(member);

                System.out.println("✅ Default Member User Created");
                System.out.println("Email:    member@library.com");
                System.out.println("Password: member123");
                System.out.println("User ID:   " + savedUser.getId());
                System.out.println("Member ID: " + savedMember.getId());
                System.out.println("========================================");
            }
        };
    }
}