package com.pragma.loans.infrastructure.adapters;

import com.pragma.loans.domain.model.Customer;
import com.pragma.loans.domain.model.Loan;
import com.pragma.loans.domain.ports.LoanRepositoryPort;
import com.pragma.loans.infrastructure.entities.CustomerEntity;
import com.pragma.loans.infrastructure.entities.LoanEntity;
import com.pragma.loans.infrastructure.repositories.JpaLoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LoanJpaAdapter implements LoanRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(LoanJpaAdapter.class);

    private final JpaLoanRepository jpaLoanRepository;

    public LoanJpaAdapter(JpaLoanRepository jpaLoanRepository) {
        this.jpaLoanRepository = jpaLoanRepository;
    }

    @Override
    public Loan save(Loan loan) {
        log.debug("Guardando préstamo: {}", loan.id());
        LoanEntity entity = toEntity(loan);
        LoanEntity savedEntity = jpaLoanRepository.save(entity);
        log.info("Préstamo {} guardado exitosamente", savedEntity.getId());
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Loan> findById(UUID id) {
        log.debug("Buscando préstamo por ID: {}", id);
        return jpaLoanRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Loan> findByCustomerId(UUID customerId) {
        log.debug("Buscando préstamos para cliente: {}", customerId);
        return jpaLoanRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByStatus(String status) {
        log.debug("Buscando préstamos con estado: {}", status);
        return jpaLoanRepository.findByStatus(status)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findAll() {
        log.debug("Obteniendo todos los préstamos");
        return jpaLoanRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Loan update(Loan loan) {
        log.debug("Actualizando préstamo: {}", loan.id());
        if (!jpaLoanRepository.existsById(loan.id())) {
            log.error("Préstamo no encontrado para actualizar: {}", loan.id());
            throw new IllegalArgumentException("Préstamo no encontrado: " + loan.id());
        }
        LoanEntity entity = toEntity(loan);
        LoanEntity updatedEntity = jpaLoanRepository.save(entity);
        log.info("Préstamo {} actualizado exitosamente", updatedEntity.getId());
        return toDomain(updatedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        log.debug("Eliminando préstamo: {}", id);
        if (!jpaLoanRepository.existsById(id)) {
            log.warn("Préstamo no encontrado para eliminar: {}", id);
            throw new IllegalArgumentException("Préstamo no encontrado: " + id);
        }
        jpaLoanRepository.deleteById(id);
        log.info("Préstamo {} eliminado exitosamente", id);
    }

    private LoanEntity toEntity(Loan loan) {
        CustomerEntity customerEntity = null;
        if (loan.customer() != null) {
            Customer customer = loan.customer();
            customerEntity = new CustomerEntity();
            customerEntity.setId(customer.id());
            customerEntity.setFirstName(customer.firstName());
            customerEntity.setLastName(customer.lastName());
            customerEntity.setEmail(customer.email());
            customerEntity.setCreditScore(customer.creditScore());
            customerEntity.setCreatedAt(customer.createdAt());
        }

        LoanEntity entity = new LoanEntity();
        entity.setId(loan.id());
        entity.setCustomer(customerEntity);
        entity.setAmount(loan.amount());
        entity.setTermMonths(loan.termMonths());
        entity.setPurpose(loan.purpose());
        entity.setStatus(loan.status());
        entity.setApplicationDate(loan.applicationDate());
        entity.setDueDate(loan.dueDate());
        entity.setOutstandingBalance(loan.outstandingBalance());

        if (loan.fraudCheckId() != null) {
            entity.setFraudCheckId(loan.fraudCheckId());
        }
        if (loan.riskReportId() != null) {
            entity.setRiskReportId(loan.riskReportId());
        }

        return entity;
    }

    private Loan toDomain(LoanEntity entity) {
        Customer customer = null;
        if (entity.getCustomer() != null) {
            CustomerEntity customerEntity = entity.getCustomer();
            customer = new Customer(
                customerEntity.getId(),
                customerEntity.getFirstName(),
                customerEntity.getLastName(),
                customerEntity.getEmail(),
                customerEntity.getCreditScore(),
                customerEntity.getCreatedAt()
            );
        }

        return new Loan(
            entity.getId(),
            customer,
            entity.getAmount(),
            entity.getTermMonths(),
            entity.getPurpose(),
            entity.getStatus(),
            entity.getApplicationDate(),
            entity.getDueDate(),
            entity.getOutstandingBalance(),
            entity.getFraudCheckId(),
            entity.getRiskReportId()
        );
    }
}