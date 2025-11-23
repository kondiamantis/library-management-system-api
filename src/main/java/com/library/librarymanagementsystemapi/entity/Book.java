package com.library.librarymanagementsystemapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.librarymanagementsystemapi.converters.BookGenreConverter;
import com.library.librarymanagementsystemapi.enums.BookGenre;
import com.library.librarymanagementsystemapi.enums.BookStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 1, max = 100, message = "Author name must be between 1 and 100 characters")
    @Column(nullable = false)
    private String author;

    @NotBlank(message = "ISBN is required")
//    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
//            message = "Invalid ISBN format")
    @Column(unique = true, nullable = false)
    private String isbn;

    @Min(value = 1000, message = "Publication year must be valid")
    @Max(value = 2100, message = "Publication year must be valid")
    @Column(name = "publication_year")
    private Integer publicationYear;


    @NotNull(message = "Genre is required")
    @Column(name = "genre", length = 50)
    @Convert(converter = BookGenreConverter.class)
    private BookGenre genre;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @Column(name = "cover_url")
    private String coverUrl;

    @Min(value = 0, message = "Total copies cannot be negative")
    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Min(value = 0, message = "Available copies cannot be negative")
    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BookStatus status;

    @PrePersist
    @PreUpdate
    private void validateCopies() {
        if (availableCopies > totalCopies) {
            throw new IllegalStateException("Available copies cannot exceed total copies");
        }
    }

    // Calculate status after loading from DB
    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateStatus() {
        this.status = BookStatus.fromAvailableCopies(this.availableCopies);
    }
}