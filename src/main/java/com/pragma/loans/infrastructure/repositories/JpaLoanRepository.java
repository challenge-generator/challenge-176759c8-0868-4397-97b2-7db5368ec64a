package com.pragma.loans.infrastructure.repositories;

import com.pragma.loans.infrastructure.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaLoanRepository extends JpaRepository<LoanEntity, UUID> {

    @Query("SELECT l FROM LoanEntity l WHERE l.customerId = :customerId AND l.status = :status")
    List<LoanEntity> findByCustomerIdAndStatus(@Param("customerId") UUID customerId, 
                                                @Param("status") String status);

    @Query("SELECT l FROM LoanEntity l WHERE l.status = :status AND l.createdAt BETWEEN :startDate AND :endDate")
    List<LoanEntity> findByStatusAndDateRange(@Param("status") String status,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    @Query("SELECT l FROM LoanEntity l WHERE l.outstandingBalance > :minBalance")
    List<LoanEntity> findByOutstandingBalanceGreaterThan(@Param("minBalance") BigDecimal minBalance);

    @Query("SELECT l FROM LoanEntity l WHERE l.customerId = :customerId ORDER BY l.createdAt DESC")
    List<LoanEntity> findByCustomerIdOrderByCreatedAtDesc(@Param("customerId") UUID customerId);

    @Query("SELECT COUNT(l) FROM LoanEntity l WHERE l.status = :status")
    long countByStatus(@Param("status") String status);

    @Query("SELECT l FROM LoanEntity l WHERE l.fraudCheckId IS NOT NULL AND l.riskReportId IS NULL")
    List<LoanEntity> findPendingRiskAssessment();

    @Query("SELECT l FROM LoanEntity l WHERE l.status = 'APPROVED' AND l.outstandingBalance > 0")
    List<LoanEntity> findActiveLoans();

    Optional<LoanEntity> findByLoanNumber(String loanNumber);
}