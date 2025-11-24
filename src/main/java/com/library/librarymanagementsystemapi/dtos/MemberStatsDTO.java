package com.library.librarymanagementsystemapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberStatsDTO {
    private long totalBooksBorrowed;
    private long currentlyBorrowed;
    private long booksReturned;
    private long overdueBooks;
}