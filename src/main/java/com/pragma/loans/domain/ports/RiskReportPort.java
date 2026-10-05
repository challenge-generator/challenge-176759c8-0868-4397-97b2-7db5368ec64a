package com.pragma.loans.domain.ports;

import com.pragma.loans.domain.model.RiskReport;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto para operaciones del buró de riesgos.
 * Define la interfaz que el dominio necesita para consultar informes de riesgo.
 * La implementación concreta vive en infraestructura.
 */
public interface RiskReportPort {

    /**
     * Consulta un informe de riesgo por su identificador único.
     * @param riskReportId identificador del informe de riesgo
     * @return Optional con el informe si existe, vacío si no
     */
    Optional<RiskReport> findById(UUID riskReportId);

    /**
     * Consulta el informe de riesgo más reciente para un cliente específico.
     * @param customerId identificador del cliente
     * @return Optional con el informe más reciente si existe
     */
    Optional<RiskReport> findLatestByCustomerId(UUID customerId);

    /**
     * Guarda un nuevo informe de riesgo en el sistema del buró.
     * @param riskReport informe a persistir
     * @return informe guardado con su ID asignado
     */
    RiskReport save(RiskReport riskReport);

    /**
     * Verifica si existe un informe de riesgo válido para el cliente
     * que cumpla con los criterios de vigencia definidos por el negocio.
     * @param customerId identificador del cliente
     * @param maxAgeDays antigüedad máxima permitida del informe
     * @return true si existe un informe vigente
     */
    boolean hasValidReport(UUID customerId, int maxAgeDays);
}