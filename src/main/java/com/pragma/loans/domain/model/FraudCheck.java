package com.pragma.loans.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record FraudCheck(
    @NotNull
    UUID fraudCheckId,
    
    @NotNull
    UUID loanId,
    
    @NotNull
    @Size(min = 3, max = 20)
    String status,
    
    @NotNull
    @PastOrPresent
    LocalDateTime checkDate,
    
    @NotNull
    @Size(max = 500)
    String reason,
    
    @NotNull
    Boolean isFlagged,
    
    @Size(max = 200)
    String fraudType,
    
    @NotNull
    Integer fraudScore
) {
    public FraudCheck {
        if (fraudScore < 0 || fraudScore > 1000) {
            throw new IllegalArgumentException("Fraud score must be between 0 and 1000");
        }
        if (checkDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Check date cannot be in the future");
        }
    }
    
    public FraudCheck withStatus(String newStatus, String newReason) {
        return new FraudCheck(
            this.fraudCheckId,
            this.loanId,
            newStatus,
            LocalDateTime.now(),
            newReason,
            this.isFlagged,
            this.fraudType,
            this.fraudScore
        );
    }
    
    public FraudCheck withFraudDetails(String fraudType, int fraudScore) {
        if (fraudScore < 0 || fraudScore > 1000) {
            throw new IllegalArgumentException("Fraud score must be between 0 and 1000");
        }
        return new FraudCheck(
            this.fraudCheckId,
            this.loanId,
            this.status,
            this.checkDate,
            this.reason,
            fraudScore > 700,
            fraudType,
            fraudScore
        );
    }
}