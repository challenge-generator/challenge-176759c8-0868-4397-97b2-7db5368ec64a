package com.pragma.loans.infrastructure.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fraud_check", indexes = {
    @Index(name = "idx_fraud_check_loan_application", columnList = "loan_application_id"),
    @Index(name = "idx_fraud_check_status", columnList = "status"),
    @Index(name = "idx_fraud_check_created_at", columnList = "created_at")
})
public class FraudCheckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "loan_application_id", nullable = false)
    private UUID loanApplicationId;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(length = 500)
    private String reason;

    @Column(name = "fraud_type", length = 100)
    private String fraudType;

    @Column(name = "fraud_score")
    private Integer fraudScore;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public FraudCheckEntity() {
    }

    public FraudCheckEntity(UUID loanApplicationId, String status, String reason,
                           String fraudType, Integer fraudScore) {
        this.loanApplicationId = loanApplicationId;
        this.status = status;
        this.reason = reason;
        this.fraudType = fraudType;
        this.fraudScore = fraudScore;
        this.createdAt = LocalDateTime.now();
        this.checkedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(UUID loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFraudType() {
        return fraudType;
    }

    public void setFraudType(String fraudType) {
        this.fraudType = fraudType;
    }

    public Integer getFraudScore() {
        return fraudScore;
    }

    public void setFraudScore(Integer fraudScore) {
        this.fraudScore = fraudScore;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}