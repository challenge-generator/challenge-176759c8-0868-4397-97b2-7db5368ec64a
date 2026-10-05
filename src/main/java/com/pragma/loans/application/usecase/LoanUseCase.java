package com.pragma.loans.application.usecase;

import com.pragma.loans.domain.model.Customer;
import com.pragma.loans.domain.model.FraudCheck;
import com.pragma.loans.domain.model.Loan;
import com.pragma.loans.domain.model.RiskReport;
import com.pragma.loans.domain.ports.FraudCheckPort;
import com.pragma.loans.domain.ports.LoanRepositoryPort;
import com.pragma.loans.domain.ports.RiskReportPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanUseCase {

    private static final Logger log = LoggerFactory.getLogger(LoanUseCase.class);
    private static final String STATUS_PENDING = "PENDING";
    private static final String STATUS_APPROVED = "APPROVED";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final String STATUS_FRAUD_DETECTED = "FRAUD_DETECTED";
    private static final String FRAUD_CHECK_STATUS_APPROVED = "APPROVED";
    private static final String FRAUD_CHECK_STATUS_REJECTED = "REJECTED";

    private final LoanRepositoryPort loanRepository;
    private final FraudCheckPort fraudCheckPort;
    private final RiskReportPort riskReportPort;

    public LoanUseCase(LoanRepositoryPort loanRepository,
                       FraudCheckPort fraudCheckPort,
                       RiskReportPort riskReportPort) {
        this.loanRepository = loanRepository;
        this.fraudCheckPort = fraudCheckPort;
        this.riskReportPort = riskReportPort;
    }

    @Transactional
    public Loan createLoan(String customerId, BigDecimal amount, int termMonths,
                          String purpose, String customerFirstName, String customerLastName,
                          String customerEmail, int customerCreditScore) {
        log.info("Iniciando proceso de creación de préstamo para cliente: {}", customerId);

        Customer customer = new Customer(
            UUID.fromString(customerId),
            customerFirstName,
            customerLastName,
            customerEmail,
            customerCreditScore,
            LocalDate.now()
        );

        Loan loan = new Loan(
            UUID.randomUUID(),
            customer,
            amount,
            termMonths,
            purpose,
            STATUS_PENDING,
            LocalDate.now(),
            LocalDate.now().plusMonths(termMonths),
            amount,
            null,
            null
        );

        Loan savedLoan = loanRepository.save(loan);
        log.info("Préstamo {} creado con estado inicial: {}", savedLoan.id(), savedLoan.status());

        return savedLoan;
    }

    @Transactional
    public Loan processFraudCheck(UUID loanId) {
        log.info("Iniciando verificación antifraude para préstamo: {}", loanId);

        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            log.error("Préstamo no encontrado: {}", loanId);
            throw new IllegalArgumentException("Préstamo no encontrado: " + loanId);
        }

        Loan loan = loanOpt.get();
        if (!STATUS_PENDING.equals(loan.status())) {
            log.warn("Préstamo {} no está en estado PENDING, estado actual: {}", loanId, loan.status());
            throw new IllegalStateException("El préstamo no puede ser procesado en estado: " + loan.status());
        }

        FraudCheck fraudCheck = fraudCheckPort.executeFraudCheck(loan);
        FraudCheck savedCheck = fraudCheckPort.save(fraudCheck);

        Loan updatedLoan;
        if (FRAUD_CHECK_STATUS_REJECTED.equals(savedCheck.status())) {
            updatedLoan = loan
                .withFraudCheckId(savedCheck.id())
                .withStatus(STATUS_FRAUD_DETECTED);
            log.warn("Fraude detectado en préstamo {}: tipo={}, score={}",
                loanId, savedCheck.fraudType(), savedCheck.fraudScore());
        } else {
            updatedLoan = loan
                .withFraudCheckId(savedCheck.id())
                .withStatus(STATUS_APPROVED);
            log.info("Verificación antifraude aprobada para préstamo: {}", loanId);
        }

        return loanRepository.update(updatedLoan);
    }

    @Transactional
    public RiskReport generateRiskReport(UUID loanId) {
        log.info("Generando informe de riesgo para préstamo: {}", loanId);

        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Préstamo no encontrado: " + loanId);
        }

        Loan loan = loanOpt.get();
        RiskReport riskReport = riskReportPort.generateReport(loan);

        Loan updatedLoan = loan.withRiskReportId(riskReport.id());
        loanRepository.update(updatedLoan);

        log.info("Informe de riesgo {} generado para préstamo: {}", riskReport.id(), loanId);
        return riskReport;
    }

    @Transactional(readOnly = true)
    public Optional<Loan> findLoanById(UUID loanId) {
        log.debug("Buscando préstamo por ID: {}", loanId);
        return loanRepository.findById(loanId);
    }

    @Transactional(readOnly = true)
    public List<Loan> findLoansByCustomerId(UUID customerId) {
        log.debug("Buscando préstamos para cliente: {}", customerId);
        return loanRepository.findByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<Loan> findLoansByStatus(String status) {
        log.debug("Buscando préstamos con estado: {}", status);
        return loanRepository.findByStatus(status);
    }

    @Transactional
    public Loan updateLoanStatus(UUID loanId, String newStatus) {
        log.info("Actualizando estado del préstamo {} a: {}", loanId, newStatus);

        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Préstamo no encontrado: " + loanId);
        }

        Loan loan = loanOpt.get();
        Loan updatedLoan = loan.withStatus(newStatus);
        return loanRepository.update(updatedLoan);
    }

    @Transactional
    public void makePayment(UUID loanId, BigDecimal amount) {
        log.info("Procesando pago de {} para préstamo: {}", amount, loanId);

        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isEmpty()) {
            throw new IllegalArgumentException("Préstamo no encontrado: " + loanId);
        }

        Loan loan = loanOpt.get();
        BigDecimal currentBalance = loan.outstandingBalance();

        if (amount.compareTo(currentBalance) > 0) {
            log.warn("Monto del pago {} excede el saldo pendiente: {}", amount, currentBalance);
            throw new IllegalArgumentException("El monto del pago excede el saldo pendiente");
        }

        BigDecimal newBalance = currentBalance.subtract(amount);
        Loan updatedLoan = loan.withOutstandingBalance(newBalance);
        loanRepository.update(updatedLoan);

        log.info("Pago procesado. Nuevo saldo del préstamo {}: {}", loanId, newBalance);
    }

    @Transactional(readOnly = true)
    public List<Loan> findAllLoans() {
        log.debug("Obteniendo todos los préstamos");
        return loanRepository.findAll();
    }

    @Transactional
    public void deleteLoan(UUID loanId) {
        log.info("Eliminando préstamo: {}", loanId);
        loanRepository.deleteById(loanId);
    }
}