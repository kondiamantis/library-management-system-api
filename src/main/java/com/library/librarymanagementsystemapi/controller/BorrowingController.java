package com.library.librarymanagementsystemapi.controller;

import com.library.librarymanagementsystemapi.dtos.BorrowingRequest;
import com.library.librarymanagementsystemapi.entity.Borrowing;
import com.library.librarymanagementsystemapi.service.BorrowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "Borrowings", description = "Book borrowing and returning management")
public class BorrowingController {

    private final BorrowingService borrowingService;

    @GetMapping
    @Operation(summary = "Get all borrowings", description = "Retrieve a list of all borrowing records")
    @ApiResponse(responseCode = "200", description = "List of borrowings retrieved successfully")
    public ResponseEntity<List<Borrowing>> getAllBorrowings() {
        return ResponseEntity.ok(borrowingService.getAllBorrowings());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get borrowing by ID", description = "Retrieve a specific borrowing record by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Borrowing retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Borrowing not found")
    })
    public ResponseEntity<Borrowing> getBorrowingById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.getBorrowingById(id));
    }

    @PostMapping
    @Operation(summary = "Borrow a book", description = "Create a new borrowing record for a member")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Book borrowed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid borrowing request"),
        @ApiResponse(responseCode = "404", description = "Member or book not found")
    })
    public ResponseEntity<Borrowing> borrowBook(@Valid @RequestBody BorrowingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingService.borrowBook(request));
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return a borrowed book", description = "Mark a borrowed book as returned")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book returned successfully"),
        @ApiResponse(responseCode = "404", description = "Borrowing not found")
    })
    public ResponseEntity<Borrowing> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.returnBook(id));
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "Get borrowings by member", description = "Retrieve all borrowing records for a specific member")
    @ApiResponse(responseCode = "200", description = "Member borrowings retrieved successfully")
    public ResponseEntity<List<Borrowing>> getBorrowingsByMember(@PathVariable @Parameter(description = "Member ID") Long memberId) {
        return ResponseEntity.ok(borrowingService.getBorrowingsByMember(memberId));
    }

    @GetMapping("/member/{memberId}/active")
    @Operation(summary = "Get active borrowings by member", description = "Retrieve only active (not returned) borrowing records for a member")
    @ApiResponse(responseCode = "200", description = "Active borrowings retrieved successfully")
    public ResponseEntity<List<Borrowing>> getActiveBorrowingsByMember(@PathVariable @Parameter(description = "Member ID") Long memberId) {
        return ResponseEntity.ok(borrowingService.getActiveBorrowingsByMember(memberId));
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get borrowings by book", description = "Retrieve all borrowing records for a specific book")
    @ApiResponse(responseCode = "200", description = "Book borrowings retrieved successfully")
    public ResponseEntity<List<Borrowing>> getBorrowingsByBook(@PathVariable @Parameter(description = "Book ID") Long bookId) {
        return ResponseEntity.ok(borrowingService.getBorrowingsByBook(bookId));
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue borrowings", description = "Retrieve all overdue borrowing records")
    @ApiResponse(responseCode = "200", description = "Overdue borrowings retrieved successfully")
    public ResponseEntity<List<Borrowing>> getOverdueBorrowings() {
        return ResponseEntity.ok(borrowingService.getOverdueBorrowings());
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get borrowings by status", description = "Retrieve borrowing records by their status (BORROWED, RETURNED, OVERDUE)")
    @ApiResponse(responseCode = "200", description = "Borrowings retrieved successfully")
    public ResponseEntity<List<Borrowing>> getBorrowingsByStatus(@PathVariable @Parameter(description = "Borrowing status") String status) {
        return ResponseEntity.ok(borrowingService.getBorrowingsByStatus(status));
    }

    @PutMapping("/update-overdue-status")
    @Operation(summary = "Update overdue statuses", description = "Update all borrowing records that have exceeded their due date")
    @ApiResponse(responseCode = "200", description = "Overdue statuses updated successfully")
    public ResponseEntity<Void> updateOverdueStatus() {
        borrowingService.updateOverdueStatus();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete borrowing", description = "Remove a borrowing record")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Borrowing deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Borrowing not found")
    })
    public ResponseEntity<Void> deleteBorrowing(@PathVariable Long id) {
        borrowingService.deleteBorrowing(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get borrowings by user ID", description = "Retrieve all borrowing records for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User borrowings retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<Borrowing>> getBorrowingsByUserId(@PathVariable @Parameter(description = "User ID") Long userId) {
        try {
            List<Borrowing> borrowings = borrowingService.getBorrowingsByUserId(userId);
            return ResponseEntity.ok(borrowings);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
