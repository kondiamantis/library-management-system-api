package com.library.librarymanagementsystemapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    private long totalBooks;
    private long availableBooks;
    private long borrowedBooks;
    private long totalMembers;
    private long activeMembers;
    private long activeBorrowings;
    private long overdueBorrowings;
    private double totalLateFees;
}