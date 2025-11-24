package com.library.librarymanagementsystemapi.service;

import com.library.librarymanagementsystemapi.dtos.BorrowingRequest;
import com.library.librarymanagementsystemapi.entity.Book;
import com.library.librarymanagementsystemapi.entity.Borrowing;
import com.library.librarymanagementsystemapi.entity.Member;
import com.library.librarymanagementsystemapi.enums.BorrowingStatus;
import com.library.librarymanagementsystemapi.exception.ResourceNotFoundException;
import com.library.librarymanagementsystemapi.repository.BookRepository;
import com.library.librarymanagementsystemapi.repository.BorrowingRepository;
import com.library.librarymanagementsystemapi.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookService bookService;
    private final MemberService memberService;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    private static final double LATE_FEE_PER_DAY = 0.50; // 0.50 per day late

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public Borrowing getBorrowingById(Long id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing not found with id: " + id));
    }

    @Transactional
    public Borrowing borrowBook(BorrowingRequest request) {
        // Validate that either userId or memberId is provided
        if (request.getUserId() == null && request.getMemberId() == null) {
            throw new RuntimeException("Either userId or memberId must be provided");
        }

        // Find book
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if book is available
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book is not available for borrowing");
        }

        // Find member - either by userId (member borrowing) or memberId (admin borrowing for someone)
        Member member;
        if (request.getUserId() != null) {
            // Member borrowing for themselves - find by user ID
            member = memberRepository.findByUserId(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("Member profile not found for this user. Please contact support."));
        } else {
            // Admin borrowing for someone - find by member ID
            member = memberRepository.findById(request.getMemberId())
                    .orElseThrow(() -> new RuntimeException("Member not found with id: " + request.getMemberId()));
        }

        // Create borrowing
        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setMember(member);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(request.getBorrowingDays()));
        borrowing.setStatus(BorrowingStatus.BORROWED);
        borrowing.setLateFee(0.0);

        // Update book available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        return borrowingRepository.save(borrowing);
    }

    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = getBorrowingById(borrowingId);

        if ("RETURNED".equals(borrowing.getStatus())) {
            throw new IllegalStateException("Book has already been returned");
        }

        // Set return date
        borrowing.setReturnDate(LocalDate.now());
        borrowing.setStatus(BorrowingStatus.RETURNED);

        // Calculate late fee if overdue
        if (borrowing.getReturnDate().isAfter(borrowing.getDueDate())) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(
                    borrowing.getDueDate(),
                    borrowing.getReturnDate()
            );
            borrowing.setLateFee(daysLate * LATE_FEE_PER_DAY);
        }

        // Increase available copies
        Book book = borrowing.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookService.updateBook(book.getId(), book);

        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> getBorrowingsByMember(Long memberId) {
        memberService.getMemberById(memberId); // Verify member exists
        return borrowingRepository.findByMemberId(memberId);
    }

    public List<Borrowing> getBorrowingsByBook(Long bookId) {
        bookService.getBookById(bookId); // Verify book exists
        return borrowingRepository.findByBookId(bookId);
    }

    public List<Borrowing> getActiveBorrowingsByMember(Long memberId) {
        return borrowingRepository.findActiveBorrowingsByMember(memberId);
    }

    public List<Borrowing> getOverdueBorrowings() {
        return borrowingRepository.findOverdueBorrowings(LocalDate.now());
    }

    public List<Borrowing> getBorrowingsByStatus(String status) {
        return borrowingRepository.findByStatus(status);
    }

    public void updateOverdueStatus() {
        List<Borrowing> overdueBorrowings = borrowingRepository.findOverdueBorrowings(LocalDate.now());
        for (Borrowing borrowing : overdueBorrowings) {
            if ("BORROWED".equals(borrowing.getStatus())) {
                borrowing.setStatus(BorrowingStatus.RETURNED);
                borrowingRepository.save(borrowing);
            }
        }
    }

    public void deleteBorrowing(Long id) {
        Borrowing borrowing = getBorrowingById(id);
        borrowingRepository.delete(borrowing);
    }

    public List<Borrowing> getBorrowingsByUserId(Long userId) {
        // First, find the member linked to this user
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Member profile not found for this user"));

        // Then get all borrowings for this member
        return borrowingRepository.findByMemberId(member.getId());
    }
}