package com.pragma.loans.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RiskReport(
    UUID id,
    UUID loanApplicationId,
    String bureauCode,
    int riskScore,
    String riskLevel,
    BigDecimal creditExposure,
    int delinquencyHistory,
    int totalAccounts,
    int activeAccounts,
    int closedAccounts,
    BigDecimal totalOutstanding,
    BigDecimal totalPastDue,
    int inquiriesLast6Months,
    String legalActionsFlag,
    String collectionsFlag,
    String bankruptcyFlag,
    LocalDateTime reportDate,
    String dataSource,
    String verificationStatus,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public RiskReport withRiskScore(int newRiskScore) {
        return new RiskReport(
            this.id,
            this.loanApplicationId,
            this.bureauCode,
            newRiskScore,
            this.riskLevel,
            this.creditExposure,
            this.delinquencyHistory,
            this.totalAccounts,
            this.activeAccounts,
            this.closedAccounts,
            this.totalOutstanding,
            this.totalPastDue,
            this.inquiriesLast6Months,
            this.legalActionsFlag,
            this.collectionsFlag,
            this.bankruptcyFlag,
            this.reportDate,
            this.dataSource,
            this.verificationStatus,
            this.createdAt,
            this.updatedAt
        );
    }

    public RiskReport withVerificationStatus(String newStatus) {
        return new RiskReport(
            this.id,
            this.loanApplicationId,
            this.bureauCode,
            this.riskScore,
            this.riskLevel,
            this.creditExposure,
            this.delinquencyHistory,
            this.totalAccounts,
            this.activeAccounts,
            this.closedAccounts,
            this.totalOutstanding,
            this.totalPastDue,
            this.inquiriesLast6Months,
            this.legalActionsFlag,
            this.collectionsFlag,
            this.bankruptcyFlag,
            this.reportDate,
            this.dataSource,
            newStatus,
            this.createdAt,
            LocalDateTime.now()
        );
    }

    public RiskReport withRiskLevel(String newRiskLevel) {
        return new RiskReport(
            this.id,
            this.loanApplicationId,
            this.bureauCode,
            this.riskScore,
            newRiskLevel,
            this.creditExposure,
            this.delinquencyHistory,
            this.totalAccounts,
            this.activeAccounts,
            this.closedAccounts,
            this.totalOutstanding,
            this.totalPastDue,
            this.inquiriesLast6Months,
            this.legalActionsFlag,
            this.collectionsFlag,
            this.bankruptcyFlag,
            this.reportDate,
            this.dataSource,
            this.verificationStatus,
            this.createdAt,
            this.updatedAt
        );
    }

    public boolean isHighRisk() {
        return "HIGH".equalsIgnoreCase(this.riskLevel) || this.riskScore >= 700;
    }

    public boolean hasLegalIssues() {
        return "Y".equalsIgnoreCase(this.legalActionsFlag) || 
               "Y".equalsIgnoreCase(this.collectionsFlag) ||
               "Y".equalsIgnoreCase(this.bankruptcyFlag);
    }

    public boolean isDelinquent() {
        return this.delinquencyHistory > 0 || 
               (this.totalPastDue != null && this.totalPastDue.compareTo(BigDecimal.ZERO) > 0);
    }

    public static RiskReport create(UUID loanApplicationId, String bureauCode) {
        return new RiskReport(
            UUID.randomUUID(),
            loanApplicationId,
            bureauCode,
            0,
            "PENDING",
            BigDecimal.ZERO,
            0,
            0,
            0,
            0,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            0,
            "N",
            "N",
            "N",
            LocalDateTime.now(),
            bureauCode,
            "PENDING",
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }
}