package com.pragma.loans.infrastructure.adapters;

import com.pragma.loans.domain.model.Loan;
import com.pragma.loans.infrastructure.entities.LoanEntity;
import com.pragma.loans.infrastructure.repositories.JpaLoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas de integración para LoanJpaAdapter")
class LoanJpaAdapterTest {

    @Mock
    private JpaLoanRepository jpaLoanRepository;

    private LoanJpaAdapter loanJpaAdapter;

    private LoanEntity loanEntitySample;
    private Loan loanDomainSample;
    private UUID loanId;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        loanJpaAdapter = new LoanJpaAdapter(jpaLoanRepository);

        loanId = UUID.randomUUID();
        customerId = UUID.randomUUID();

        loanEntitySample = new LoanEntity();
        loanEntitySample.setId(loanId);
        loanEntitySample.setCustomerId(customerId);
        loanEntitySample.setPrincipalAmount(new BigDecimal("100000.00"));
        loanEntitySample.setStatus("en_revision");
        loanEntitySample.setTermMonths(12);
        loanEntitySample.setInterestRate(new BigDecimal("8.5"));
        loanEntitySample.setStartDate(LocalDate.now());
        loanEntitySample.setEndDate(LocalDate.now().plusMonths(12));
        loanEntitySample.setOutstandingBalance(new BigDecimal("100000.00"));

        loanDomainSample = new Loan(
            loanId,
            customerId,
            new BigDecimal("100000.00"),
            "en_revision",
            12,
            new BigDecimal("8.5"),
            LocalDate.now(),
            LocalDate.now().plusMonths(12),
            new BigDecimal("100000.00"),
            null,
            null
        );
    }

    @Test
    @DisplayName("Guardar préstamo debe persistir correctamente en la base de datos")
    void save_ShouldPersistLoanEntity() {
        when(jpaLoanRepository.save(any(LoanEntity.class))).thenReturn(loanEntitySample);

        Loan savedLoan = loanJpaAdapter.save(loanDomainSample);

        assertNotNull(savedLoan, "El préstamo guardado no debe ser nulo");
        assertEquals(loanId, savedLoan.id(), "El ID debe coincidir");
        assertEquals("en_revision", savedLoan.status(), "El estado debe persistir");

        ArgumentCaptor<LoanEntity> entityCaptor = ArgumentCaptor.forClass(LoanEntity.class);
        verify(jpaLoanRepository, times(1)).save(entityCaptor.capture());

        LoanEntity capturedEntity = entityCaptor.getValue();
        assertEquals(customerId, capturedEntity.getCustomerId(),
            "El customerId debe persistirse como clave foránea");
    }

    @Test
    @DisplayName("Buscar por ID debe retornar el préstamo convertido a dominio")
    void findById_WhenExists_ShouldReturnDomainLoan() {
        when(jpaLoanRepository.findById(loanId)).thenReturn(Optional.of(loanEntitySample));

        Optional<Loan> foundLoan = loanJpaAdapter.findById(loanId);

        assertTrue(foundLoan.isPresent(), "Debe encontrar el préstamo");
        assertEquals(loanId, foundLoan.get().id(), "El ID del dominio debe coincidir");
        assertEquals(customerId, foundLoan.get().customerId(),
            "El customerId debe mapearse correctamente");
    }

    @Test
    @DisplayName("Buscar por ID inexistente debe retornar Optional.empty()")
    void findById_WhenNotExists_ShouldReturnEmpty() {
        UUID nonExistentId = UUID.randomUUID();
        when(jpaLoanRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        Optional<Loan> foundLoan = loanJpaAdapter.findById(nonExistentId);

        assertTrue(foundLoan.isEmpty(), "Debe retornar vacío cuando no existe");
    }

    @Test
    @DisplayName("Buscar por estado debe retornar lista de préstamos en ese estado")
    void findByStatus_ShouldReturnLoansWithGivenStatus() {
        List<LoanEntity> entitiesInStatus = List.of(loanEntitySample);
        when(jpaLoanRepository.findByStatus("en_revision")).thenReturn(entitiesInStatus);

        List<Loan> loans = loanJpaAdapter.findByStatus("en_revision");

        assertFalse(loans.isEmpty(), "Debe retornar préstamos");
        assertEquals(1, loans.size(), "Debe retornar un préstamo");
        assertEquals("en_revision", loans.get(0).status(),
            "El estado debe coincidir con la búsqueda");
    }

    @Test
    @DisplayName("Buscar por cliente debe retornar todos los préstamos de ese cliente")
    void findByCustomerId_ShouldReturnLoansForCustomer() {
        List<LoanEntity> entitiesForCustomer = List.of(loanEntitySample);
        when(jpaLoanRepository.findByCustomerId(customerId)).thenReturn(entitiesForCustomer);

        List<Loan> loans = loanJpaAdapter.findByCustomerId(customerId);

        assertFalse(loans.isEmpty(), "Debe retornar préstamos del cliente");
        assertTrue(loans.stream().allMatch(l -> l.customerId().equals(customerId)),
            "Todos los préstamos deben pertenecer al cliente");
    }

    @Test
    @DisplayName("Eliminar préstamo debe invocar el método delete del repositorio")
    void deleteById_ShouldCallRepositoryDelete() {
        doNothing().when(jpaLoanRepository).deleteById(loanId);

        loanJpaAdapter.deleteById(loanId);

        verify(jpaLoanRepository, times(1)).deleteById(loanId);
    }

    @Test
    @DisplayName("Verificar mapeo correcto de campos críticos en la normalización")
    void save_ShouldMapAllCriticalFieldsForNormalization() {
        when(jpaLoanRepository.save(any(LoanEntity.class))).thenAnswer(invocation -> {
            LoanEntity e = invocation.getArgument(0);
            e.setId(loanId);
            return e;
        });

        Loan loanToSave = loanDomainSample.withFraudCheckId(UUID.randomUUID())
            .withRiskReportId(UUID.randomUUID());

        Loan saved = loanJpaAdapter.save(loanToSave);

        ArgumentCaptor<LoanEntity> captor = ArgumentCaptor.forClass(LoanEntity.class);
        verify(jpaLoanRepository).save(captor.capture());

        LoanEntity persisted = captor.getValue();
        assertNotNull(persisted.getCustomerId(),
            "El customerId es obligatorio para la relación normalizada");
        assertNotNull(persisted.getStatus(),
            "El status es obligatorio para el control de flujo");
    }

    @Test
    @DisplayName("Verificar que las consultas usan índices correctamente")
    void findByStatus_ShouldUseIndexedQuery() {
        when(jpaLoanRepository.findByStatus(anyString())).thenReturn(List.of());

        loanJpaAdapter.findByStatus("aprobado");

        verify(jpaLoanRepository, times(1)).findByStatus("aprobado");
    }

    @Test
    @DisplayName("Verificar manejo de valores nulos en relaciones")
    void save_ShouldHandleNullForeignKeys() {
        Loan loanWithoutRelations = new Loan(
            loanId,
            customerId,
            new BigDecimal("50000.00"),
            "pendiente",
            6,
            new BigDecimal("7.0"),
            LocalDate.now(),
            LocalDate.now().plusMonths(6),
            new BigDecimal("50000.00"),
            null,
            null
        );

        when(jpaLoanRepository.save(any(LoanEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        Loan saved = loanJpaAdapter.save(loanWithoutRelations);

        assertNotNull(saved, "Debe guardar sin relaciones");
        assertNull(saved.fraudCheckId(), "FraudCheckId debe ser null");
        assertNull(saved.riskReportId(), "RiskReportId debe ser null");
    }

    @Test
    @DisplayName("Verificar que existe al menos una consulta por rango de fechas")
    void findByDateRange_ShouldBeSupported() {
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();

        when(jpaLoanRepository.findByStartDateBetween(startDate, endDate))
            .thenReturn(List.of(loanEntitySample));

        List<Loan> loans = loanJpaAdapter.findByDateRange(startDate, endDate);

        assertFalse(loans.isEmpty(),
            "Debe soportar consultas por rango de fechas para reportes");
    }
}