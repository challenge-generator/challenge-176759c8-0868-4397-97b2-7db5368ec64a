package com.pragma.loans.domain.ports;

import com.pragma.loans.domain.model.Loan;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepositoryPort {
    
    Loan save(Loan loan);
    
    Optional<Loan> findById(UUID id);
    
    List<Loan> findByCustomerId(UUID customerId);
    
    List<Loan> findByStatus(String status);
    
    List<Loan> findByCustomerIdAndStatus(UUID customerId, String status);
    
    boolean existsById(UUID id);
    
    void deleteById(UUID id);
    
    List<Loan> findLoansExpiringBefore(java.time.LocalDateTime date);
    
    List<Loan> findLoansWithOutstandingBalanceGreaterThan(java.math.BigDecimal amount);
    
    long countByStatus(String status);
    
    List<Loan> findAll(int page, int size);
    
    List<Loan> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);
}"// === ARCHIVO: src/main/java/com/pragma/loans/domain/ports/FraudCheckPort.java ===
package com.pragma.loans.domain.ports;

import com.pragma.loans.domain.model.FraudCheck;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FraudCheckPort {
    
    FraudCheck save(FraudCheck fraudCheck);
    
    Optional<FraudCheck> findById(UUID id);
    
    Optional<FraudCheck> findByLoanApplicationId(UUID loanApplicationId);
    
    List<FraudCheck> findByCustomerId(UUID customerId);
    
    List<FraudCheck> findByStatus(String status);
    
    List<FraudCheck> findByCustomerIdAndStatus(UUID customerId, String status);
    
    boolean existsByLoanApplicationId(UUID loanApplicationId);
    
    void deleteById(UUID id);
    
    List<FraudCheck> findFraudChecksExceedingScore(int minScore);
    
    long countByStatus(String status);
    
    List<FraudCheck> findByCustomerIdOrderByCreatedAtDesc(UUID customerId);
}