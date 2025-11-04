package com.library.librarymanagementsystemapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be between 10 and 15 digits")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;

    @NotNull(message = "Membership date is required")
    @Column(name = "membership_date", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate membershipDate;

    @Column(name = "membership_expiry_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate membershipExpiryDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @PrePersist
    private void setDefaultMembershipDate() {
        if (membershipDate == null) {
            membershipDate = LocalDate.now();
        }
        if (membershipExpiryDate == null) {
            membershipExpiryDate = LocalDate.now().plusYears(1);
        }
    }
}
