package com.library.librarymanagementsystemapi.controller;

import com.library.librarymanagementsystemapi.dtos.MemberStatsDTO;
import com.library.librarymanagementsystemapi.entity.Member;
import com.library.librarymanagementsystemapi.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(member));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @Valid @RequestBody Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
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
