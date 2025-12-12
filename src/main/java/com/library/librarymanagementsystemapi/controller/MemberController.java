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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Members", description = "Member management endpoints")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @Operation(summary = "Get all members", description = "Retrieve a list of all library members")
    @ApiResponse(responseCode = "200", description = "List of members retrieved successfully")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID", description = "Retrieve a specific member by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Member retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PostMapping
    @Operation(summary = "Create new member", description = "Register a new library member with user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Member created successfully"),
        @ApiResponse(responseCode = "400", description = "Email already exists or invalid request")
    })
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
    @Operation(summary = "Update member", description = "Update an existing member's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Member updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request or duplicate email"),
        @ApiResponse(responseCode = "404", description = "Member not found")
    })
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
    @Operation(summary = "Delete member", description = "Remove a member from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Member deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search members", description = "Search members by name, email, or phone number")
    @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    public ResponseEntity<List<Member>> searchMembers(@RequestParam @Parameter(description = "Search query") String query) {
        return ResponseEntity.ok(memberService.searchMembers(query));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active members", description = "Retrieve all active members")
    @ApiResponse(responseCode = "200", description = "Active members retrieved successfully")
    public ResponseEntity<List<Member>> getActiveMembers() {
        return ResponseEntity.ok(memberService.getActiveMembers());
    }

    @GetMapping("/inactive")
    @Operation(summary = "Get inactive members", description = "Retrieve all inactive members")
    @ApiResponse(responseCode = "200", description = "Inactive members retrieved successfully")
    public ResponseEntity<List<Member>> getInactiveMembers() {
        return ResponseEntity.ok(memberService.getInactiveMembers());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get member by email", description = "Retrieve a member by their email address")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Member retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public ResponseEntity<Member> getMemberByEmail(@PathVariable @Parameter(description = "Member email") String email) {
        return ResponseEntity.ok(memberService.getMemberByEmail(email));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get member by user ID", description = "Retrieve member information associated with a user account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Member retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public ResponseEntity<Member> getMemberByUserId(@PathVariable @Parameter(description = "User ID") Long userId) {
        Member member = memberService.getMemberByUserId(userId);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{memberId}/stats")
    @Operation(summary = "Get member statistics", description = "Retrieve borrowing statistics for a member")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Member statistics retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public ResponseEntity<MemberStatsDTO> getMemberStats(@PathVariable @Parameter(description = "Member ID") Long memberId) {
        MemberStatsDTO stats = memberService.getMemberStats(memberId);
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}/toggle-status")
    @Operation(summary = "Toggle member status", description = "Activate or deactivate a member account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Member status toggled successfully"),
        @ApiResponse(responseCode = "404", description = "Member not found")
    })
    public ResponseEntity<Member> toggleMemberStatus(@PathVariable Long id) {
        Member member = memberService.toggleMemberStatus(id);
        return ResponseEntity.ok(member);
    }
}
