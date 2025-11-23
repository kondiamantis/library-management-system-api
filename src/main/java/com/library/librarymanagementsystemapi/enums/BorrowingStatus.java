package com.library.librarymanagementsystemapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BorrowingStatus {
    BORROWED("BORROWED"),
    RETURNED("RETURNED"),
    OVERDUE("OVERDUE");

    private final String value;

    BorrowingStatus(String value) {
        this.value = value;
    }

    @JsonValue  // Serializes as "BORROWED", "RETURNED", "OVERDUE"
    public String getValue() {
        return value;
    }

    public static BorrowingStatus fromString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        for (BorrowingStatus status : BorrowingStatus.values()) {
            if (status.value.equalsIgnoreCase(value) ||
                    status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid borrowing status: " + value);
    }
}