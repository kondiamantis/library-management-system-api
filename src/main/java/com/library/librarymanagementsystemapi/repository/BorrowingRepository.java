package com.library.librarymanagementsystemapi.repository;

import com.library.librarymanagementsystemapi.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    List<Borrowing> findByMemberId(Long memberId);

    List<Borrowing> findByBookId(Long bookId);

    List<Borrowing> findByStatus(String status);

    @Query("SELECT b FROM Borrowing b WHERE b.status = 'BORROWED' AND b.dueDate < :currentDate")
    List<Borrowing> findOverdueBorrowings(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT b FROM Borrowing b WHERE b.member.id = :memberId AND b.status = 'BORROWED'")
    List<Borrowing> findActiveBorrowingsByMember(@Param("memberId") Long memberId);

    @Query("SELECT b FROM Borrowing b WHERE b.book.id = :bookId AND b.status = 'BORROWED'")
    List<Borrowing> findActiveBorrowingsByBook(@Param("bookId") Long bookId);

    @Query("SELECT COUNT(b) FROM Borrowing b WHERE b.status = 'BORROWED'")
    long countActiveBorrowings();

    @Query("SELECT COUNT(b) FROM Borrowing b WHERE b.status = 'BORROWED' AND b.dueDate < :currentDate")
    long countOverdueBorrowings(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT b.book.id, COUNT(b) as borrowCount FROM Borrowing b GROUP BY b.book.id ORDER BY borrowCount DESC")
    List<Object[]> findMostBorrowedBooks();
}