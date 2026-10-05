package com.pragma.loans.infrastructure.adapters;

import com.pragma.loans.domain.model.FraudCheck;
import com.pragma.loans.domain.model.Loan;
import com.pragma.loans.domain.ports.FraudCheckPort;
import com.pragma.loans.infrastructure.entities.FraudCheckEntity;
import com.pragma.loans.infrastructure.repositories.JpaFraudCheckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FraudCheckAdapter implements FraudCheckPort {

    private static final Logger log = LoggerFactory.getLogger(FraudCheckAdapter.class);
    private static final String STATUS_APPROVED = "APPROVED";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final String FRAUD_TYPE_IDENTITY = "IDENTITY_FRAUD";
    private static final String FRAUD_TYPE_APPLICATION = "APPLICATION_FRAUD";
    private static final String FRAUD_TYPE_NONE = "NONE";

    private final JpaFraudCheckRepository fraudCheckRepository;

    public FraudCheckAdapter(JpaFraudCheckRepository fraudCheckRepository) {
        this.fraudCheckRepository = fraudCheckRepository;
    }

    @Override
    public FraudCheck executeFraudCheck(Loan loan) {
        log.info("Ejecutando verificación antifraude para préstamo: {}", loan.id());

        String customerEmail = loan.customer() != null ? loan.customer().email() : "";
        String customerId = loan.customer() != null ? loan.customer().id().toString() : "";

        boolean flagged = shouldFlagForFraud(loan);

        FraudCheck fraudCheck;
        if (flagged) {
            String fraudType = determineFraudType(loan);
            int fraudScore = calculateFraudScore(loan, fraudType);

            fraudCheck = new FraudCheck(
                UUID.randomUUID(),
                loan.id(),
                customerId,
                customerEmail,
                STATUS_REJECTED,
                "Application flagged by fraud detection engine",
                fraudType,
                fraudScore,
                LocalDateTime.now()
            );

            log.warn("Verificación antifraude rechazada para préstamo {}: tipo={}, score={}",
                loan.id(), fraudType, fraudScore);
        } else {
            fraudCheck = new FraudCheck(
                UUID.randomUUID(),
                loan.id(),
                customerId,
                customerEmail,
                STATUS_APPROVED,
                "No fraud indicators detected",
                FRAUD_TYPE_NONE,
                0,
                LocalDateTime.now()
            );

            log.info("Verificación antifraude aprobada para préstamo: {}", loan.id());
        }

        return fraudCheck;
    }

    @Override
    public FraudCheck save(FraudCheck fraudCheck) {
        log.debug("Guardando verificación antifraude: {}", fraudCheck.id());
        FraudCheckEntity entity = toEntity(fraudCheck);
        FraudCheckEntity savedEntity = fraudCheckRepository.save(entity);
        log.info("Verificación antifraude {} guardada con estado: {}",
            savedEntity.getId(), savedEntity.getStatus());
        return toDomain(savedEntity);
    }

    @Override
    public Optional<FraudCheck> findById(UUID id) {
        log.debug("Buscando verificación antifraude por ID: {}", id);
        return fraudCheckRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<FraudCheck> findByLoanId(UUID loanId) {
        log.debug("Buscando verificación antifraude para préstamo: {}", loanId);
        return fraudCheckRepository.findByLoanId(loanId).map(this::toDomain);
    }

    private boolean shouldFlagForFraud(Loan loan) {
        if (loan.customer() == null) {
            return true;
        }

        if (loan.amount() != null && loan.amount().compareTo(java.math.BigDecimal.valueOf(100000)) > 0) {
            log.debug("Monto alto detectado: {}", loan.amount());
        }

        if (loan.customer().creditScore() < 500) {
            log.debug("Score de crédito bajo: {}", loan.customer().creditScore());
            return true;
        }

        String email = loan.customer().email();
        if (email != null && (email.contains("test") || email.contains("fake") || email.contains("invalid"))) {
            log.debug("Email sospechoso: {}", email);
            return true;
        }

        return false;
    }

    private String determineFraudType(Loan loan) {
        if (loan.customer() != null && loan.customer().creditScore() < 500) {
            return FRAUD_TYPE_IDENTITY;
        }

        String email = loan.customer() != null ? loan.customer().email() : "";
        if (email.contains("test") || email.contains("fake")) {
            return FRAUD_TYPE_APPLICATION;
        }

        return FRAUD_TYPE_NONE;
    }

    private int calculateFraudScore(Loan loan, String fraudType) {
        int baseScore = 0;

        if (FRAUD_TYPE_IDENTITY.equals(fraudType)) {
            baseScore = 75;
        } else if (FRAUD_TYPE_APPLICATION.equals(fraudType)) {
            baseScore = 60;
        }

        if (loan.amount() != null && loan.amount().compareTo(java.math.BigDecimal.valueOf(50000)) > 0) {
            baseScore += 10;
        }

        if (loan.customer() != null && loan.customer().creditScore() < 400) {
            baseScore += 15;
        }

        return Math.min(baseScore, 100);
    }

    private FraudCheckEntity toEntity(FraudCheck fraudCheck) {
        FraudCheckEntity entity = new FraudCheckEntity();
        entity.setId(fraudCheck.id());
        entity.setLoanId(fraudCheck.loanId());
        entity.setCustomerId(fraudCheck.customerId());
        entity.setCustomerEmail(fraudCheck.customerEmail());
        entity.setStatus(fraudCheck.status());
        entity.setReason(fraudCheck.reason());
        entity.setFraudType(fraudCheck.fraudType());
        entity.setFraudScore(fraudCheck.fraudScore());
        entity.setCheckedAt(fraudCheck.checkedAt());
        return entity;
    }

    private FraudCheck toDomain(FraudCheckEntity entity) {
        return new FraudCheck(
            entity.getId(),
            entity.getLoanId(),
            entity.getCustomerId(),
            entity.getCustomerEmail(),
            entity.getStatus(),
            entity.getReason(),
            entity.getFraudType(),
            entity.getFraudScore(),
            entity.getCheckedAt()
        );
    }
}