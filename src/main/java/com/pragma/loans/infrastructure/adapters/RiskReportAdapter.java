package com.pragma.loans.infrastructure.adapters;

import com.pragma.loans.domain.model.RiskReport;
import com.pragma.loans.domain.ports.RiskReportPort;
import com.pragma.loans.infrastructure.entities.RiskReportEntity;
import com.pragma.loans.infrastructure.repositories.JpaRiskReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class RiskReportAdapter implements RiskReportPort {

    private static final Logger log = LoggerFactory.getLogger(RiskReportAdapter.class);
    private static final String BUREAU_NAME = "CREDIT_BUREAU";
    private static final int DEFAULT_RISK_SCORE = 500;
    private static final String DEFAULT_RISK_CATEGORY = "NORMAL";

    private final JpaRiskReportRepository riskReportRepository;

    public RiskReportAdapter(JpaRiskReportRepository riskReportRepository) {
        this.riskReportRepository = riskReportRepository;
    }

    @Override
    public RiskReport requestRiskReport(UUID customerId, BigDecimal requestedAmount) {
        log.info("Solicitando informe de riesgo para cliente {} con monto {}", customerId, requestedAmount);
        
        int riskScore = calculateRiskScore(customerId, requestedAmount);
        String riskCategory = categorizeRisk(riskScore);
        boolean isEligible = riskScore >= 600 && requestedAmount.compareTo(getMaxAllowedAmount(customerId)) <= 0;
        
        RiskReportEntity entity = new RiskReportEntity();
        entity.setId(UUID.randomUUID());
        entity.setCustomerId(customerId);
        entity.setBureauName(BUREAU_NAME);
        entity.setRiskScore(riskScore);
        entity.setRiskCategory(riskCategory);
        entity.setEligible(isEligible);
        entity.setRequestedAmount(requestedAmount);
        entity.setMaxAllowedAmount(getMaxAllowedAmount(customerId));
        entity.setReportDate(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
        
        RiskReportEntity saved = riskReportRepository.save(entity);
        log.info("Informe de riesgo generado con score {} y categoría {} para cliente {}", 
                riskScore, riskCategory, customerId);
        
        return toDomain(saved);
    }

    @Override
    public Optional<RiskReport> getRiskReportById(UUID reportId) {
        log.debug("Consultando informe de riesgo con ID {}", reportId);
        return riskReportRepository.findById(reportId).map(this::toDomain);
    }

    @Override
    public Optional<RiskReport> getLatestRiskReport(UUID customerId) {
        log.debug("Consultando último informe de riesgo para cliente {}", customerId);
        return riskReportRepository.findTopByCustomerIdOrderByReportDateDesc(customerId)
                .map(this::toDomain);
    }

    private int calculateRiskScore(UUID customerId, BigDecimal requestedAmount) {
        int baseScore = DEFAULT_RISK_SCORE;
        
        if (requestedAmount.compareTo(BigDecimal.valueOf(50000)) > 0) {
            baseScore -= 100;
        } else if (requestedAmount.compareTo(BigDecimal.valueOf(10000)) > 0) {
            baseScore -= 50;
        }
        
        if (requestedAmount.compareTo(BigDecimal.valueOf(100000)) > 0) {
            baseScore -= 150;
        }
        
        return Math.max(300, Math.min(850, baseScore));
    }

    private String categorizeRisk(int riskScore) {
        if (riskScore >= 750) {
            return "EXCELLENT";
        } else if (riskScore >= 700) {
            return "GOOD";
        } else if (riskScore >= 650) {
            return "FAIR";
        } else if (riskScore >= 600) {
            return "ACCEPTABLE";
        } else {
            return DEFAULT_RISK_CATEGORY;
        }
    }

    private BigDecimal getMaxAllowedAmount(UUID customerId) {
        return BigDecimal.valueOf(50000);
    }

    private RiskReport toDomain(RiskReportEntity entity) {
        return new RiskReport(
                entity.getId(),
                entity.getCustomerId(),
                entity.getBureauName(),
                entity.getRiskScore(),
                entity.getRiskCategory(),
                entity.isEligible(),
                entity.getRequestedAmount(),
                entity.getMaxAllowedAmount(),
                entity.getReportDate()
        );
    }
}