package com.library.librarymanagementsystemapi.controller;

import com.library.librarymanagementsystemapi.dtos.MemberStatsDTO;
import com.library.librarymanagementsystemapi.entity.Member;
import com.library.librarymanagementsystemapi.entity.User;
import com.library.librarymanagementsystemapi.enums.Role;
import com.library.librarymanagementsystemapi.exception.DuplicateResourceException;
import com.library.librarymanagementsystemapi.repository.MemberRepository;
import com.library.librarymanagementsystemapi.repository.UserRepository;
import com.library.librarymanagementsystemapi.service.MemberService;
import com.library.librarymanagementsystemapi.dtos.CreateMemberRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PostMapping
    public ResponseEntity<?> createMember(@Valid @RequestBody CreateMemberRequest request) {

        try{
            // Check if email already exists
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Email already exists");
            }

            // Create and save user
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setRole(Role.MEMBER);
            user.setIsActive(true);

            User savedUser = userRepository.save(user);

            // Create corresponding Member record
            Member member = new Member();
            member.setUser(savedUser);
            member.setFirstName(request.getFirstName());
            member.setLastName(request.getLastName());
            member.setEmail(request.getEmail());
            member.setPhoneNumber(request.getPhoneNumber());
            member.setAddress(request.getAddress() != null ? request.getAddress() : "");
            member.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

            Member savedMember = memberRepository.save(member);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create member:" + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @Valid @RequestBody Member member) {
        try {
            Member updatedMember = memberService.updateMember(id, member);
            return ResponseEntity.ok(updatedMember);
        } catch (DuplicateResourceException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update member: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam String query) {
        return ResponseEntity.ok(memberService.searchMembers(query));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Member>> getActiveMembers() {
        return ResponseEntity.ok(memberService.getActiveMembers());
    }

    @GetMapping("/inactive")
    public ResponseEntity<List<Member>> getInactiveMembers() {
        return ResponseEntity.ok(memberService.getInactiveMembers());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberByEmail(email));
    }

    // Get member by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Member> getMemberByUserId(@PathVariable Long userId) {
        Member member = memberService.getMemberByUserId(userId);
        return ResponseEntity.ok(member);
    }

    // Get member statistics
    @GetMapping("/{memberId}/stats")
    public ResponseEntity<MemberStatsDTO> getMemberStats(@PathVariable Long memberId) {
        MemberStatsDTO stats = memberService.getMemberStats(memberId);
        return ResponseEntity.ok(stats);
    }

    // Toggle member status (activate/deactivate)
    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<Member> toggleMemberStatus(@PathVariable Long id) {
        Member member = memberService.toggleMemberStatus(id);
        return ResponseEntity.ok(member);
    }
}
