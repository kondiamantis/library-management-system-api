package com.library.librarymanagementsystemapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BookStatus {

    AVAILABLE("available"),
    LOW_STOCK("low"),
    OUT_OF_STOCK("out");

    private final String value;

    BookStatus(String value) {
        this.value = value;
    }

    @JsonValue //Serializes as "available", "low", "out"
    public String getValue() {
        return value;
    }

    public static BookStatus fromAvailableCopies(int availableCopies) {
        if (availableCopies == 0) {
            return OUT_OF_STOCK;
        } else if (availableCopies <= 2) {
            return LOW_STOCK;
        } else {
            return AVAILABLE;
        }
    }
}
