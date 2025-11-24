package com.library.librarymanagementsystemapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ISBNValidator implements ConstraintValidator<ValidISBN, String> {

    @Override
    public void initialize(ValidISBN constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }

        // Remove hyphens and spaces
        String cleanISBN = isbn.replaceAll("[\\s-]", "");

        // Validate ISBN-10: 10 characters, 9 digits + 1 digit or X
        if (cleanISBN.length() == 10) {
            return cleanISBN.matches("^[0-9]{9}[0-9X]$");
        }

        // Validate ISBN-13: 13 digits, must start with 978 or 979
        if (cleanISBN.length() == 13) {
            return cleanISBN.matches("^(978|979)[0-9]{10}$");
        }

        return false;
    }
}