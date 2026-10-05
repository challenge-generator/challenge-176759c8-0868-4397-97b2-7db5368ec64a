package com.pragma.loans.application.usecase;



import com.pragma.loans.domain.model.RiskReport;
import com.pragma.loans.domain.model.FraudCheck;
import com.pragma.loans.domain.model.Customer;
import com.pragma.loans.domain.model.Loan;
import com.pragma.loans.domain.ports.LoanRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias para LoanUseCase")
class LoanUseCaseTest {

    @Mock
    private LoanRepositoryPort loanRepositoryPort;

    @InjectMocks
    private LoanUseCase loanUseCase;

    private Loan loanSample;
    private Customer customerSample;
    private UUID loanId;

    @BeforeEach
    void setUp() {
        loanId = UUID.randomUUID();
        customerSample = new Customer(
            UUID.randomUUID(),
            "Juan",
            "Pérez",
            "juan.perez@email.com",
            "CC",
            "12345678",
            650,
            50000.00,
            LocalDate.of(1990, 5, 15),
            "activo"
        );

        loanSample = new Loan(
            loanId,
            customerSample.id(),
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
    @DisplayName("Crear préstamo exitosamente cuando el cliente existe y tiene score válido")
    void createLoan_WhenCustomerExistsAndHasValidScore_ShouldCreateLoan() {
        when(loanRepositoryPort.save(any(Loan.class))).thenReturn(loanSample);

        Loan createdLoan = loanUseCase.createLoan(loanSample);

        assertNotNull(createdLoan, "El préstamo creado no debe ser nulo");
        assertEquals(loanId, createdLoan.id(), "El ID del préstamo debe coincidir");
        assertEquals("en_revision", createdLoan.status(), "El estado inicial debe ser en_revision");
        assertEquals(new BigDecimal("100000.00"), createdLoan.principalAmount(), "El monto debe ser el mismo");
        verify(loanRepositoryPort, times(1)).save(any(Loan.class));
    }

    @Test
    @DisplayName("Buscar préstamo por ID existente debe retornar el préstamo")
    void findById_WhenLoanExists_ShouldReturnLoan() {
        when(loanRepositoryPort.findById(loanId)).thenReturn(Optional.of(loanSample));

        Optional<Loan> foundLoan = loanUseCase.findById(loanId);

        assertTrue(foundLoan.isPresent(), "Debe encontrar el préstamo");
        assertEquals(loanId, foundLoan.get().id(), "El ID debe coincidir");
        verify(loanRepositoryPort, times(1)).findById(loanId);
    }

    @Test
    @DisplayName("Buscar préstamo por ID inexistente debe retornar vacío")
    void findById_WhenLoanDoesNotExist_ShouldReturnEmpty() {
        UUID nonExistentId = UUID.randomUUID();
        when(loanRepositoryPort.findById(nonExistentId)).thenReturn(Optional.empty());

        Optional<Loan> foundLoan = loanUseCase.findById(nonExistentId);

        assertTrue(foundLoan.isEmpty(), "No debe encontrar ningún préstamo");
        verify(loanRepositoryPort, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Actualizar estado del préstamo correctamente")
    void updateLoanStatus_ShouldUpdateStatus() {
        String newStatus = "aprobado";
        when(loanRepositoryPort.findById(loanId)).thenReturn(Optional.of(loanSample));
        when(loanRepositoryPort.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Loan> updatedLoan = loanUseCase.updateLoanStatus(loanId, newStatus);

        assertTrue(updatedLoan.isPresent(), "El préstamo debe existir");
        assertEquals(newStatus, updatedLoan.get().status(), "El estado debe haber cambiado");
        verify(loanRepositoryPort, times(1)).save(any(Loan.class));
    }

    @Test
    @DisplayName("Calcular saldo pendiente con pagos realizados correctamente")
    void calculateOutstandingBalance_WhenPaymentsMade_ShouldComputeCorrectly() {
        Loan loanWithPayments = new Loan(
            loanId,
            customerSample.id(),
            new BigDecimal("100000.00"),
            "aprobado",
            12,
            new BigDecimal("8.5"),
            LocalDate.now(),
            LocalDate.now().plusMonths(12),
            new BigDecimal("85000.00"),
            null,
            null
        );

        BigDecimal outstanding = loanUseCase.calculateOutstandingBalance(loanWithPayments);

        assertEquals(0, new BigDecimal("85000.00").compareTo(outstanding),
            "El saldo pendiente debe ser 85000.00");
    }

    @Test
    @DisplayName("Verificar que el préstamo requiere normalización de datos")
    void loanModel_ShouldSupportNormalization() {
        assertNotNull(loanSample.id(), "El ID es obligatorio para la normalización");
        assertNotNull(loanSample.customerId(), "El customerId es obligatorio para la relación");
        assertNotNull(loanSample.status(), "El status es obligatorio para el control");

        Loan normalizedLoan = loanSample.withStatus("aprobado");
        assertEquals("aprobado", normalizedLoan.status(), "El estado debe actualizarse");
        assertEquals(loanSample.id(), normalizedLoan.id(), "El ID no debe cambiar");

        assertDoesNotThrow(() -> {
            loanSample.withFraudCheckId(UUID.randomUUID());
        }, "Debe soportar asociación con verificación de fraude");

        assertDoesNotThrow(() -> {
            loanSample.withRiskReportId(UUID.randomUUID());
        }, "Debe soportar asociación con reporte de riesgo");
    }

    @Test
    @DisplayName("Validar que el modelo soporta consultas por diferentes criterios")
    void findLoansByStatus_ShouldSupportQueryByStatus() {
        when(loanRepositoryPort.findByStatus("en_revision")).thenReturn(java.util.List.of(loanSample));

        var loans = loanUseCase.findLoansByStatus("en_revision");

        assertFalse(loans.isEmpty(), "Debe retornar préstamos en revisión");
        assertEquals("en_revision", loans.get(0).status(), "Todos deben estar en revisión");
        verify(loanRepositoryPort, times(1)).findByStatus("en_revision");
    }

    @Test
    @DisplayName("Validar integridad referencial del modelo normalizado")
    void validateReferentialIntegrity_ShouldWorkWithRelatedEntities() {
        UUID customerId = loanSample.customerId();
        assertNotNull(customerId, "El customerId es la clave foránea hacia customer");

        assertDoesNotThrow(() -> {
            Loan linkedLoan = loanSample.withFraudCheckId(UUID.randomUUID());
            assertNotNull(linkedLoan.fraudCheckId(), "Debe soportar link a FraudCheck");
        }, "El modelo debe soportar la relación con FraudCheck");

        assertDoesNotThrow(() -> {
            Loan linkedLoan = loanSample.withRiskReportId(UUID.randomUUID());
            assertNotNull(linkedLoan.riskReportId(), "Debe soportar link a RiskReport");
        }, "El modelo debe soportar la relación con RiskReport");
    }
}