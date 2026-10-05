package com.pragma.loans.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Customer(
    @NotNull
    UUID customerId,
    
    @NotNull
    @Size(min = 2, max = 100)
    String firstName,
    
    @NotNull
    @Size(min = 2, max = 100)
    String lastName,
    
    @NotNull
    @Email
    String email,
    
    @NotNull
    @Pattern(regexp = "^[0-9]{10,15}$")
    String phoneNumber,
    
    @NotNull
    @Size(min = 10, max = 200)
    String address,
    
    @NotNull
    @Past
    LocalDate dateOfBirth,
    
    @NotNull
    @Size(min = 3, max = 20)
    String identificationType,
    
    @NotNull
    @Size(min = 5, max = 30)
    String identificationNumber,
    
    @NotNull
    LocalDate createdAt,
    
    @NotNull
    LocalDate updatedAt,
    
    @NotNull
    String employmentStatus,
    
    @NotNull
    Integer creditScore
) {
    public Customer {
        if (dateOfBirth.isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("Customer must be at least 18 years old");
        }
        if (createdAt.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Created date cannot be in the future");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Updated date cannot be before created date");
        }
        if (creditScore < 300 || creditScore > 850) {
            throw new IllegalArgumentException("Credit score must be between 300 and 850");
        }
    }
    
    public Customer withUpdatedInfo(String newFirstName, String newLastName, String newEmail,
                                   String newPhoneNumber, String newAddress, String newEmploymentStatus) {
        return new Customer(
            this.customerId,
            newFirstName,
            newLastName,
            newEmail,
            newPhoneNumber,
            newAddress,
            this.dateOfBirth,
            this.identificationType,
            this.identificationNumber,
            this.createdAt,
            LocalDate.now(),
            newEmploymentStatus,
            this.creditScore
        );
    }
    
    public Customer withCreditScore(int newCreditScore) {
        if (newCreditScore < 300 || newCreditScore > 850) {
            throw new IllegalArgumentException("Credit score must be between 300 and 850");
        }
        return new Customer(
            this.customerId,
            this.firstName,
            this.lastName,
            this.email,
            this.phoneNumber,
            this.address,
            this.dateOfBirth,
            this.identificationType,
            this.identificationNumber,
            this.createdAt,
            LocalDate.now(),
            this.employmentStatus,
            newCreditScore
        );
    }
}