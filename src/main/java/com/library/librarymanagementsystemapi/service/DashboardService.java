package com.library.librarymanagementsystemapi.service;

import com.library.librarymanagementsystemapi.dtos.DashboardStats;
import com.library.librarymanagementsystemapi.entity.Borrowing;
import com.library.librarymanagementsystemapi.repository.BookRepository;
import com.library.librarymanagementsystemapi.repository.BorrowingRepository;
import com.library.librarymanagementsystemapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowingRepository borrowingRepository;

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();

        // Book statistics
        stats.setTotalBooks(bookRepository.count());
        stats.setAvailableBooks(bookRepository.findByAvailableCopiesGreaterThan(0).size());

        // Member statistics
        stats.setTotalMembers(memberRepository.count());
        stats.setActiveMembers(memberRepository.countByIsActive(true));

        // Borrowing statistics
        stats.setActiveBorrowings(borrowingRepository.countActiveBorrowings());
        stats.setOverdueBorrowings(borrowingRepository.countOverdueBorrowings(LocalDate.now()));

        // Calculate borrowed books (total copies - available copies)
        long borrowedBooks = bookRepository.findAll().stream()
                .mapToLong(book -> book.getTotalCopies() - book.getAvailableCopies())
                .sum();
        stats.setBorrowedBooks(borrowedBooks);

        // Calculate total late fees
        double totalLateFees = borrowingRepository.findAll().stream()
                .mapToDouble(Borrowing::getLateFee)
                .sum();
        stats.setTotalLateFees(totalLateFees);

        return stats;
    }
}