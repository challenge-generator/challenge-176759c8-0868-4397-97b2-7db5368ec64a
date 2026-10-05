# Modelo de Datos Actual - Análisis de Debilidades y Redundancias

## Descripción del Sistema

El sistema de gestión de préstamos actual opera con un modelo de datos que fue diseñado rápidamente sin considerar las principios de normalización. Este documento analiza las debilidades estructurales que han causado problemas de consistencia, rendimiento y mantenimiento en la base de datos.

## Modelo de Datos Actual (No Normalizado)

### Estructura de la Tabla Principal

```sql
CREATE TABLE loan_application (
    id BIGSERIAL PRIMARY KEY,
    customer_first_name VARCHAR(100) NOT NULL,
    customer_last_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_id_number VARCHAR(50) NOT NULL,
    customer_phone VARCHAR(20),
    customer_address TEXT,
    customer_city VARCHAR(100),
    customer_country VARCHAR(100),
    credit_score INT,
    monthly_income DECIMAL(15,2),
    monthly_expenses DECIMAL(15,2),
    employment_type VARCHAR(50),
    company_name VARCHAR(200),
    job_title VARCHAR(100),
    years_at_job INT,
    
    loan_amount DECIMAL(15,2) NOT NULL,
    loan_term_months INT NOT NULL,
    interest_rate DECIMAL(5,4) NOT NULL,
    monthly_payment DECIMAL(15,2),
    total_payment DECIMAL(15,2),
    loan_purpose VARCHAR(100),
    loan_status VARCHAR(50) DEFAULT 'PENDING',
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approval_date TIMESTAMP,
    disbursement_date TIMESTAMP,
    
    fraud_check_status VARCHAR(50),
    fraud_check_reason TEXT,
    fraud_check_score INT,
    fraud_check_date TIMESTAMP,
    fraud_check_type VARCHAR(100),
    
    risk_report_status VARCHAR(50),
    risk_report_score INT,
    risk_report_date TIMESTAMP,
    risk_report_details TEXT,
    risk_bureau_name VARCHAR(100),
    
    outstanding_balance DECIMAL(15,2),
    last_payment_date TIMESTAMP,
    next_payment_date TIMESTAMP,
    payment_history TEXT,
    
    assigned_agent_id BIGINT,
    assigned_agent_name VARCHAR(100),
    assigned_branch VARCHAR(100),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Análisis de Anomalías y Problemas

### Anomalía de Inserción

La tabla actual presenta problemas graves al insertar nuevos registros. Cuando se registra una nueva solicitud de préstamo, los datos del cliente deben repetirse en cada fila incluso si el mismo cliente tiene múltiples solicitudes. Esto consume espacio innecesario y dificulta la validación de datos consistentes.

Los datos del motor antifraude y del buró de riesgos no pueden existir de forma independiente. Si una solicitud de préstamo está pendiente y no se ha ejecutado ninguna verificación, los campos correspondientes quedan vacíos o con valores nulos, pero no existe forma de almacenar estas verificaciones de manera separada para su reutilización en futuras consultas.

### Anomalía de Actualización

Los datos del cliente aparecen duplicados en múltiples filas. Cuando un cliente cambia su dirección, teléfono o correo electrónico, es necesario actualizar cada fila que contenga sus datos. Esta redundancia incrementa el riesgo de inconsistencias donde algunos registros reflejan información actualizada y otros mantienen datos antiguos.

La información del agente asignado y la sucursal también se repite en cada solicitud procesada por el mismo agente. Modificar el nombre de una sucursal requiere actualizar todos los registros asociados, consumiendo recursos de la base de datos y aumentando la probabilidad de inconsistencias.

### Anomalía de Eliminación

Eliminar una solicitud de préstamo puede resultar en pérdida de información valiosa del cliente. Si un cliente decide cancelar su única solicitud y no existe otro registro asociado, todos sus datos personales se pierden del sistema. De manera similar, si se elimina un registro de verificación antifraude, se pierde el historial de análisis realizados.

## Redundancia de Datos Identificada

### Información del Cliente Duplicada

Los campos customer_first_name, customer_last_name, customer_email, customer_id_number, customer_phone, customer_address, customer_city, customer_country, credit_score, monthly_income, monthly_expenses, employment_type, company_name, job_title y years_at_job almacenan información del cliente que debería residir en una tabla independiente. Para un cliente con múltiples préstamos activos, estos datos se repiten en cada fila sin ninguna vinculación referencial.

### Datos del Agente Repetidos

Los campos assigned_agent_id, assigned_agent_name y assigned_branch se replican en cada solicitud procesada. Si un agente procesa cien solicitudes diarias, su nombre y la sucursal se almacenan cien veces de manera redundante.

### Información de Verificaciones Fragmentada

Los datos de fraud_check y risk_report se almacenan como columnas de la tabla principal. Esta estructura impide el seguimiento histórico de verificaciones, impide la reutilización de resultados y complica la generación de reportes consolidados.

## Problemas de Rendimiento

### Índices Ineficientes

La tabla actual carece de índices apropiados para las consultas más frecuentes. La búsqueda de préstamos por cliente requiere un escaneo completo de la tabla. Las consultas por estado de verificación antifraude no tienen soporte de índice, lo que resulta en tiempos de respuesta superiores al SLA establecido de 200ms cuando el volumen de datos crece.

### Desnormalización que Afecta Consultas

Los campos calculados como monthly_payment y total_payment se almacenan en la tabla. Estos valores pueden derivarse de loan_amount, loan_term_months y interest_rate mediante fórmulas matemáticas. Almacenarlos introduce la posibilidad de inconsistencias si el sistema no actualiza estos campos cuando cambian las tasas de interés.

El campo payment_history almacena una cadena de texto con todos los pagos realizados. Consultar pagos específicos o calcular totales requiere parsear esta cadena, lo cual es extremadamente ineficiente para el volumen de 1500 solicitudes por segundo que maneja el sistema.

## Impacto en la Integridad de Datos

### Ausencia de Restricciones de Integridad

El modelo actual no garantiza la integridad referencial entre entidades relacionadas. No existe forma de asegurar que un fraud_check_id apunte a un registro válido, ni de validar que los datos del cliente sean consistentes entre diferentes solicitudes del mismo cliente.

### Datos Inconsistentes

La ausencia de restricciones foreign key permite que se eliminen registros relacionados sin consecuencias controlada. Un cliente puede ser eliminado mientras sus préstamos permanecen en el sistema con información de cliente nula o inconsistente.

## Consecuencias Operativas

### Dificultad para Generación de Reportes

Los reportes consolidados por cliente, agente o sucursal requieren agregaciones complejas sobre una tabla desnormalizada. El procesamiento de estos reportes consume recursos significativos y frecuentemente supera los tiempos límite establecidos.

### Limitaciones para Análisis Histórico

No es posible analizar tendencias de fraude porque los resultados de verificaciones anteriores se sobrescriben en cada nueva solicitud. La información histórica se pierde cuando se actualiza una solicitud.

### Problemas de Mantenimiento

任何修改 en la estructura de datos del cliente requiere actualizar múltiples partes del sistema. Los scripts de migración son complejos y propensos a errores porque deben considerar la redundancia de datos.

## Conclusión del Análisis

El modelo de datos actual viola las primeras tres formas normales y presenta anomalías de inserción, actualización y eliminación que comprometen la integridad y el rendimiento del sistema. La normalización del modelo es imprescindible para cumplir con el SLA de 99.9% y la latencia máxima de 200ms con el volumen de 1500 solicitudes por segundo en hora pico.