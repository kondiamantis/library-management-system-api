package com.library.librarymanagementsystemapi.controller;

import com.library.librarymanagementsystemapi.dtos.BorrowingRequest;
import com.library.librarymanagementsystemapi.entity.Borrowing;
import com.library.librarymanagementsystemapi.service.BorrowingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class BorrowingController {

    private final BorrowingService borrowingService;

    @GetMapping
    public ResponseEntity<List<Borrowing>> getAllBorrowings() {
        return ResponseEntity.ok(borrowingService.getAllBorrowings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Borrowing> getBorrowingById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.getBorrowingById(id));
    }

    @PostMapping
    public ResponseEntity<Borrowing> borrowBook(@Valid @RequestBody BorrowingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingService.borrowBook(request));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Borrowing> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.returnBook(id));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Borrowing>> getBorrowingsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowingService.getBorrowingsByMember(memberId));
    }

    @GetMapping("/member/{memberId}/active")
    public ResponseEntity<List<Borrowing>> getActiveBorrowingsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowingService.getActiveBorrowingsByMember(memberId));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Borrowing>> getBorrowingsByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(borrowingService.getBorrowingsByBook(bookId));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Borrowing>> getOverdueBorrowings() {
        return ResponseEntity.ok(borrowingService.getOverdueBorrowings());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Borrowing>> getBorrowingsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(borrowingService.getBorrowingsByStatus(status));
    }

    @PutMapping("/update-overdue-status")
    public ResponseEntity<Void> updateOverdueStatus() {
        borrowingService.updateOverdueStatus();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowing(@PathVariable Long id) {
        borrowingService.deleteBorrowing(id);
        return ResponseEntity.noContent().build();
    }
}
