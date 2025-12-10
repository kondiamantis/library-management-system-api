package com.library.librarymanagementsystemapi.controller;

import com.library.librarymanagementsystemapi.dtos.AuthResponse;
import com.library.librarymanagementsystemapi.dtos.LoginRequest;
import com.library.librarymanagementsystemapi.dtos.SignupRequest;
import com.library.librarymanagementsystemapi.entity.Member;
import com.library.librarymanagementsystemapi.entity.User;
import com.library.librarymanagementsystemapi.enums.Role;
import com.library.librarymanagementsystemapi.repository.MemberRepository;
import com.library.librarymanagementsystemapi.repository.UserRepository;
import com.library.librarymanagementsystemapi.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Authenticate user (checks email and password)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get user from database
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if member is active (for MEMBER role only)
        Boolean isActive = true; // Default to true for admins
        if (user.getRole() == Role.MEMBER) {
            Member member = memberRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Member not found for user"));

            if (!member.getIsActive()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Your account has been deactivated. Please contact an administrator.");
            }

            isActive = member.getIsActive();
        }

        // Generate JWT token
        String jwt = tokenProvider.generateToken(authentication);

        // Create and return AuthResponse with isActive
        AuthResponse authResponse = new AuthResponse(
                jwt,
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                isActive  // Include isActive in response
        );

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        // Check if email already exists
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // Create and save user
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setRole(Role.MEMBER);
        user.setIsActive(true);

        User savedUser = userRepository.save(user);

        // Create corresponding Member record and link to user
        Member member = new Member();
        member.setUser(savedUser);  // Link to user
        member.setFirstName(signupRequest.getFirstName());
        member.setLastName(signupRequest.getLastName());
        member.setEmail(signupRequest.getEmail());
        member.setPhoneNumber("0000000000"); // Default - user can update in profile
        member.setAddress(""); // User can update in profile
        member.setIsActive(true);

        // membershipDate and membershipExpiryDate will be set by @PrePersist
        memberRepository.save(member);

        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user);
    }
}

