package com.library.librarymanagementsystemapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BookGenre {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    SCIENCE("Science"),
    TECHNOLOGY("Technology"),
    HISTORY("History"),
    BIOGRAPHY("Biography"),
    FANTASY("Fantasy"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    THRILLER("Thriller");

    private final String displayName;

    BookGenre(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static BookGenre fromString(String value) {
        for (BookGenre genre : BookGenre.values()) {
            if (genre.displayName.equalsIgnoreCase(value) ||
                    genre.name().equalsIgnoreCase(value)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid genre: " + value);
    }
}