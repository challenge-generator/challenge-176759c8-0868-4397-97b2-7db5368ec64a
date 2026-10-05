package com.pragma.loans.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record Loan(
    @NotNull
    UUID loanId,
    
    @NotNull
    UUID customerId,
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal amount,
    
    @NotNull
    @Positive
    Integer termMonths,
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal interestRate,
    
    @NotNull
    LocalDate startDate,
    
    @NotNull
    @Size(min = 3, max = 20)
    String status,
    
    @NotNull
    LocalDate createdAt,
    
    @NotNull
    LocalDate updatedAt,
    
    @NotNull
    String loanPurpose,
    
    @DecimalMin(value = "0.0")
    BigDecimal outstandingBalance,
    
    UUID fraudCheckId,
    
    UUID riskReportId
) {
    public Loan {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Loan amount must be positive");
        }
        if (termMonths <= 0) {
            throw new IllegalArgumentException("Term months must be positive");
        }
        if (interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Interest rate must be positive");
        }
        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the future");
        }
        if (createdAt.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Created date cannot be in the future");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Updated date cannot be before created date");
        }
        if (outstandingBalance.compareTo(amount) > 0) {
            throw new IllegalArgumentException("Outstanding balance cannot exceed loan amount");
        }
    }
    
    public Loan withStatus(String newStatus) {
        return new Loan(
            this.loanId,
            this.customerId,
            this.amount,
            this.termMonths,
            this.interestRate,
            this.startDate,
            newStatus,
            this.createdAt,
            LocalDate.now(),
            this.loanPurpose,
            this.outstandingBalance,
            this.fraudCheckId,
            this.riskReportId
        );
    }
    
    public Loan withFraudCheckId(UUID fraudCheckId) {
        return new Loan(
            this.loanId,
            this.customerId,
            this.amount,
            this.termMonths,
            this.interestRate,
            this.startDate,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.loanPurpose,
            this.outstandingBalance,
            fraudCheckId,
            this.riskReportId
        );
    }
    
    public Loan withRiskReportId(UUID riskReportId) {
        return new Loan(
            this.loanId,
            this.customerId,
            this.amount,
            this.termMonths,
            this.interestRate,
            this.startDate,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.loanPurpose,
            this.outstandingBalance,
            this.fraudCheckId,
            riskReportId
        );
    }
    
    public Loan withOutstandingBalance(BigDecimal newBalance) {
        if (newBalance.compareTo(amount) > 0) {
            throw new IllegalArgumentException("Outstanding balance cannot exceed loan amount");
        }
        return new Loan(
            this.loanId,
            this.customerId,
            this.amount,
            this.termMonths,
            this.interestRate,
            this.startDate,
            this.status,
            this.createdAt,
            LocalDate.now(),
            this.loanPurpose,
            newBalance,
            this.fraudCheckId,
            this.riskReportId
        );
    }
}