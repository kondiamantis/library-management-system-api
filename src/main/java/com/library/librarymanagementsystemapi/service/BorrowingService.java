package com.library.librarymanagementsystemapi.service;

import com.library.librarymanagementsystemapi.dtos.BorrowingRequest;
import com.library.librarymanagementsystemapi.entity.Book;
import com.library.librarymanagementsystemapi.entity.Borrowing;
import com.library.librarymanagementsystemapi.entity.Member;
import com.library.librarymanagementsystemapi.exception.ResourceNotFoundException;
import com.library.librarymanagementsystemapi.repository.BorrowingRepository;
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

    private static final double LATE_FEE_PER_DAY = 0.50; // 0.50 per day late

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    public Borrowing getBorrowingById(Long id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing not found with id: " + id));
    }

    public Borrowing borrowBook(BorrowingRequest request) {
        // Get book and member
        Book book = bookService.getBookById(request.getBookId());
        Member member = memberService.getMemberById(request.getMemberId());

        // Check if member is active
        if (member.getIsActive() == false){
            throw new IllegalStateException("Member is not active. Cannot borrow books. ");
        }

        // Check if book is available
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Book is not available for borrowing");
        }

        // Create borrowing
        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setMember(member);
        borrowing.setBorrowDate(LocalDate.now());

        int days = request.getBorrowingDays() != null ? request.getBorrowingDays() : 14;
        borrowing.setDueDate(LocalDate.now().plusDays(days));
        borrowing.setStatus("BORROWED");
        borrowing.setLateFee(0.0);

        // Decrease available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookService.updateBook(book.getId(), book);

        return borrowingRepository.save(borrowing);
    }

    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = getBorrowingById(borrowingId);

        if ("RETURNED".equals(borrowing.getStatus())) {
            throw new IllegalStateException("Book has already been returned");
        }

        // Set return date
        borrowing.setReturnDate(LocalDate.now());
        borrowing.setStatus("RETURNED");

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
                borrowing.setStatus("OVERDUE");
                borrowingRepository.save(borrowing);
            }
        }
    }

    public void deleteBorrowing(Long id) {
        Borrowing borrowing = getBorrowingById(id);
        borrowingRepository.delete(borrowing);
    }
}