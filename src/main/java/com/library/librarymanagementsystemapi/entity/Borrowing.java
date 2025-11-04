package com.library.librarymanagementsystemapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "borrowings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "Book is required")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull(message = "Member is required")
    private Member member;

    @Column(name = "borrow_date", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate borrowDate;

    @Column(name = "due_date", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dueDate;

    @Column(name = "return_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate returnDate;

    @Column(name = "late_fee")
    @Min(value = 0, message = "Late fee cannot be negative")
    private Double lateFee = 0.0;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "BORROWED|RETURNED|OVERDUE",
            message = "Status must be BORROWED, RETURNED, or OVERDUE")
    @Column(nullable = false)
    private String status;

    @PrePersist
    protected void onCreate() {
        if (borrowDate == null) {
            borrowDate = LocalDate.now();
        }
        if (dueDate == null) {
            dueDate = borrowDate.plusDays(14); // 14 days borrowing period
        }
        if (status == null) {
            status = "BORROWED";
        }
    }

    // Calculate if the book is overdue
    @Transient
    public boolean isOverdue() {
        return returnDate == null && LocalDate.now().isAfter(dueDate);
    }

    // Calculate days overdue
    @Transient
    public long getDaysOverdue() {
        if (returnDate != null) {
            return 0;
        }
        LocalDate now = LocalDate.now();
        return now.isAfter(dueDate) ? java.time.temporal.ChronoUnit.DAYS.between(dueDate, now) : 0;
    }
}