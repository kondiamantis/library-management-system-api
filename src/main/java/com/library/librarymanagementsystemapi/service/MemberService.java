package com.library.librarymanagementsystemapi.service;

import com.library.librarymanagementsystemapi.dtos.MemberStatsDTO;
import com.library.librarymanagementsystemapi.entity.Borrowing;
import com.library.librarymanagementsystemapi.entity.Member;
import com.library.librarymanagementsystemapi.enums.BorrowingStatus;
import com.library.librarymanagementsystemapi.exception.DuplicateResourceException;
import com.library.librarymanagementsystemapi.exception.ResourceNotFoundException;
import com.library.librarymanagementsystemapi.repository.BorrowingRepository;
import com.library.librarymanagementsystemapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BorrowingRepository borrowingRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new
                        ResourceNotFoundException("Member not found with id: " + id));
    }

    public Member createMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateResourceException("Member with email " + member.getEmail() + " already exists");
        }

        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Member member = getMemberById(id);

        // Check if email is being changed and if it already exists
        if (!member.getEmail().equals(memberDetails.getEmail()) &&
                memberRepository.existsByEmail(memberDetails.getEmail())) {
            throw new DuplicateResourceException("Member with email " + memberDetails.getEmail() + " already exists");
        }

        member.setFirstName(memberDetails.getFirstName());
        member.setLastName(memberDetails.getLastName());
        member.setEmail(memberDetails.getEmail());
        member.setPhoneNumber(memberDetails.getPhoneNumber());
        member.setAddress(memberDetails.getAddress());
        member.setMembershipDate(memberDetails.getMembershipDate());
        member.setMembershipExpiryDate(memberDetails.getMembershipExpiryDate());
        member.setIsActive(memberDetails.getIsActive());

        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        memberRepository.delete(member);
    }

    public List<Member> searchMembers(String query) {
        return memberRepository.searchMembers(query);
    }

    public List<Member> getActiveMembers() {
        return memberRepository.findByIsActive(true);
    }

    public List<Member> getInactiveMembers() {
        return memberRepository.findByIsActive(false);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with email: " + email));
    }

    public Member getMemberByUserId(Long userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Member not found for user id: " + userId));
    }

    public MemberStatsDTO getMemberStats(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        List<Borrowing> borrowings = borrowingRepository.findByMemberId(memberId);

        long totalBooksBorrowed = borrowings.size();
        long currentlyBorrowed = borrowings.stream()
                .filter(b -> b.getStatus() == BorrowingStatus.BORROWED)
                .count();
        long booksReturned = borrowings.stream()
                .filter(b -> b.getStatus() == BorrowingStatus.RETURNED)
                .count();
        long overdueBooks = borrowings.stream()
                .filter(b -> b.getStatus() == BorrowingStatus.OVERDUE)
                .count();

        return new MemberStatsDTO(totalBooksBorrowed, currentlyBorrowed, booksReturned, overdueBooks);
    }

    public Member toggleMemberStatus(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));

        member.setIsActive(!member.getIsActive());
        return memberRepository.save(member);
    }
}
