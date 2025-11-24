package com.library.librarymanagementsystemapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ISBNValidator.class)
@Documented
public @interface ValidISBN {
    String message() default "Invalid ISBN format. Must be 10 digits (ISBN-10) or 13 digits starting with 978/979 (ISBN-13)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}