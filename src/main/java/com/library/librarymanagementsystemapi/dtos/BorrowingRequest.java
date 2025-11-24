package com.library.librarymanagementsystemapi.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class BorrowingRequest {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    private Long memberId;

    private Long userId;

    // Optional: if not provided, will use default 14 days
    @Min(value = 1, message = "Borrowing days must be at least 1")
    private Integer borrowingDays;
}
