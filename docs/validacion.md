# Validación del Modelo de Datos Normalizado

## Objetivo de las Pruebas

Las pruebas de validación verifican que el modelo de datos normalizado cumple con los requisitos de integridad, rendimiento y consistencia. Se ejecutan consultas que demuestran el correcto funcionamiento de las relaciones entre entidades y la eliminación de las redundancias del modelo anterior.

## Pruebas de Integridad Referencial

### Verificación de Claves Foráneas

La primera prueba valida que las relaciones entre tablas funcionan correctamente. Se intenta insertar un pago con un identificador de préstamo inexistente, lo cual debe fallar debido a la restricción foreign key. El resultado esperado es un error de violación de restricción.

```sql
-- Prueba 1: Integridad referencial en payments
-- Objetivo: Verificar que no se puede insertar un pago para un préstamo inexistente
INSERT INTO payment (loan_id, amount, payment_date, status)
VALUES (999999, 500.00, CURRENT_TIMESTAMP, 'COMPLETED');

-- Resultado esperado: ERROR: foreign key violation
-- Código de error: 23503
-- Mensaje: insert or update on table "payment" violates 
--           foreign key constraint "payment_loan_id_fkey"
```

### Verificación de Unicidad

Los campos email e id_number en la tabla customer tienen restricción de unicidad. Esta prueba verifica que el sistema rechaza duplicados.

```sql
-- Prueba 2: Unicidad de email en customer
-- Objetivo: Verificar que no se pueden registrar dos clientes con el mismo email
INSERT INTO customer (first_name, last_name, email, id_number, phone)
VALUES ('Juan', 'Pérez', 'juan.perez@email.com', '12345678', '3001234567');

INSERT INTO customer (first_name, last_name, email, id_number, phone)
VALUES ('María', 'García', 'juan.perez@email.com', '87654321', '3007654321');

-- Resultado esperado: ERROR: duplicate key value violates unique constraint
-- Código de error: 23505
-- Mensaje: duplicate key value violates unique constraint "customer_email_key"
```

## Pruebas de Normalización

### Verificación de Primera Forma Normal

La tabla payment demuestra el cumplimiento de la primera forma normal. Cada registro contiene un solo pago con valores atómicos en todas las columnas.

```sql
-- Prueba 3: Verificación de 1FN en tabla payment
-- Objetivo: Confirmar que cada pago es un registro independiente
INSERT INTO customer (first_name, last_name, email, id_number)
VALUES ('Carlos', 'López', 'carlos.lopez@test.com', '11111111');

INSERT INTO loan (customer_id, loan_amount, loan_term_months, interest_rate, status)
VALUES (currval('customer_id_seq'), 10000.00, 12, 0.1500, 'APPROVED');

INSERT INTO payment (loan_id, amount, payment_date, status, payment_type)
VALUES (currval('loan_id_seq'), 1000.00, '2024-01-15', 'COMPLETED', 'MONTHLY');

INSERT INTO payment (loan_id, amount, payment_date, status, payment_type)
VALUES (currval('loan_id_seq'), 1000.00, '2024-02-15', 'COMPLETED', 'MONTHLY');

INSERT INTO payment (loan_id, amount, payment_date, status, payment_type)
VALUES (currval('loan_id_seq'), 1000.00, '2024-03-15', 'COMPLETED', 'MONTHLY');

-- Verificar que existen tres pagos independientes
SELECT loan_id, COUNT(*) as payment_count 
FROM payment 
GROUP BY loan_id;

-- Resultado esperado: 1 fila con payment_count = 3
```

### Verificación de Segunda Forma Normal

Los atributos de cliente en la tabla loan deben depender de la clave primaria completa (customer_id + loan_id), no solo de customer_id.

```sql
-- Prueba 4: Verificación de 2FN
-- Objetivo: Confirmar que los datos del cliente no dependen solo de customer_id
INSERT INTO customer (first_name, last_name, email, id_number)
VALUES ('Ana', 'Martínez', 'ana.martinez@test.com', '22222222');

-- Primer préstamo
INSERT INTO loan (customer_id, loan_amount, loan_term_months, interest_rate, status)
VALUES (currval('customer_id_seq'), 5000.00, 6, 0.1200, 'APPROVED');

-- Segundo préstamo del mismo cliente
INSERT INTO loan (customer_id, loan_amount, loan_term_months, interest_rate, status)
VALUES (currval('customer_id_seq'), 15000.00, 24, 0.1800, 'PENDING');

-- Verificar que los datos del cliente no están duplicados en la tabla loan
SELECT DISTINCT c.first_name, c.last_name, c.email, COUNT(l.id) as loan_count
FROM customer c
JOIN loan l ON c.id = l.customer_id
WHERE c.id_number = '22222222'
GROUP BY c.first_name, c.last_name, c.email;

-- Resultado esperado: 1 fila con loan_count = 2, datos del cliente sin duplicar
```

### Verificación de Tercera Forma Normal

Los datos del agente y la sucursal residen en sus propias tablas, eliminando dependencias transitivas.

```sql
-- Prueba 5: Verificación de 3FN
-- Objetivo: Confirmar que agent y branch son tablas independientes
INSERT INTO branch (name, code, city)
VALUES ('Sucursal Centro', 'C001', 'Bogotá');

INSERT INTO agent (name, branch_id)
VALUES ('Pedro Sánchez', currval('branch_id_seq'));

INSERT INTO customer (first_name, last_name, email, id_number)
VALUES ('Luis', 'Rodríguez', 'luis.rodriguez@test.com', '33333333');

INSERT INTO loan (customer_id, loan_amount, loan_term_months, interest_rate, status, agent_id, branch_id)
VALUES (currval('customer_id_seq'), 20000.00, 18, 0.1600, 'APPROVED', 
        currval('agent_id_seq'), currval('branch_id_seq'));

-- Verificar que la información del agente viene de la tabla agent, no está duplicada en loan
SELECT l.id as loan_id, a.name as agent_name, b.name as branch_name
FROM loan l
JOIN agent a ON l.agent_id = a.id
JOIN branch b ON l.branch_id = b.id
WHERE l.status = 'APPROVED';

-- Resultado esperado: Datos del agente y sucursal desde tablas normalizadas
```

## Pruebas de Rendimiento

### Consulta de Préstamos por Cliente

Esta consulta verifica que el índice en customer_id permite recuperar rápidamente todos los préstamos de un cliente.

```sql
-- Prueba 6: Rendimiento - Consulta de préstamos por cliente
-- Objetivo: Verificar que la consulta utiliza índices y cumple con SLA de 200ms
EXPLAIN ANALYZE
SELECT c.id, c.first_name, c.last_name, c.email, 
       l.id as loan_id, l.loan_amount, l.status, l.application_date
FROM customer c
JOIN loan l ON c.id = l.customer_id
WHERE c.id_number = '11111111'
ORDER BY l.application_date DESC;

-- Resultado esperado:
-- Planning Time: < 1ms
-- Execution Time: < 50ms
-- Uso de índice idx_customer_id_number y idx_loan_customer
```

### Búsqueda de Verificaciones por Estado

Consulta para verificar el estado de verificaciones antifraude, utilizando el índice correspondiente.

```sql
-- Prueba 7: Rendimiento - Búsqueda por estado de verificación
-- Objetivo: Confirmar uso de índice en status de fraud_check
EXPLAIN ANALYZE
SELECT fc.id, fc.status, fc.score, fc.checked_at, l.id as loan_id, c.email
FROM fraud_check fc
JOIN loan l ON fc.loan_id = l.id
JOIN customer c ON l.customer_id = c.id
WHERE fc.status = 'PENDING'
ORDER BY fc.checked_at ASC
LIMIT 100;

-- Resultado esperado:
-- Planning Time: < 2ms
-- Execution Time: < 100ms
-- Uso de índice idx_fraud_check_status
```

### Consulta de Pagos por Período

Verificación del rendimiento al consultar pagos en un rango de fechas.

```sql
-- Prueba 8: Rendimiento - Consulta de pagos por período
-- Objetivo: Verificar rendimiento con grandes volúmenes
EXPLAIN ANALYZE
SELECT p.id, p.amount, p.payment_date, p.status, l.id as loan_id, c.email
FROM payment p
JOIN loan l ON p.loan_id = l.id
JOIN customer c ON l.customer_id = c.id
WHERE p.payment_date BETWEEN '2024-01-01' AND '2024-12-31'
  AND p.status = 'COMPLETED'
ORDER BY p.payment_date DESC
LIMIT 500;

-- Resultado esperado:
-- Planning Time: < 2ms
-- Execution Time: < 150ms
-- Uso de índices idx_payment_date e idx_payment_status
```

## Pruebas de Consistencia de Datos

### Verificación de Cálculos

Los campos calculados como el saldo pendiente deben derivarse de los pagos registrados, no almacenarse redundantemente.

```sql
-- Prueba 9: Consistencia - Cálculo de saldo pendiente
-- Objetivo: Verificar que el saldo pendiente se calcula correctamente
SELECT 
    l.id as loan_id,
    l.loan_amount as original_amount,
    COALESCE(SUM(p.amount), 0) as total_paid,
    l.loan_amount - COALESCE(SUM(p.amount), 0) as calculated_balance,
    l.outstanding_balance as stored_balance,
    CASE 
        WHEN l.loan_amount - COALESCE(SUM(p.amount), 0) = l.outstanding_balance 
        THEN 'CONSISTENT' 
        ELSE 'INCONSISTENT' 
    END as balance_check
FROM loan l
LEFT JOIN payment p ON l.id = p.loan_id AND p.status = 'COMPLETED'
WHERE l.status = 'APPROVED'
GROUP BY l.id, l.loan_amount, l.outstanding_balance;

-- Resultado esperado: Todas las filas muestran 'CONSISTENT'
```

### Verificación de Historial de Estados

El modelo permite consultar el historial completo de cambios de estado de un préstamo.

```sql
-- Prueba 10: Consistencia - Consulta de información completa del préstamo
-- Objetivo: Verificar que la consulta JOIN produce información completa y coherente
SELECT 
    c.first_name || ' ' || c.last_name as customer_name,
    c.email,
    c.credit_score,
    l.id as loan_id,
    l.loan_amount,
    l.loan_term_months,
    l.interest_rate,
    l.status as loan_status,
    fc.status as fraud_status,
    fc.score as fraud_score,
    rr.status as risk_status,
    rr.score as risk_score,
    b.name as branch_name,
    a.name as agent_name
FROM customer c
JOIN loan l ON c.id = l.customer_id
LEFT JOIN fraud_check fc ON l.id = fc.loan_id
LEFT JOIN risk_report rr ON l.id = rr.loan_id
LEFT JOIN agent a ON l.agent_id = a.id
LEFT JOIN branch b ON l.branch_id = b.id
WHERE l.status IN ('PENDING', 'APPROVED', 'REJECTED')
ORDER BY l.application_date DESC
LIMIT 50;

-- Resultado esperado: 50 filas con información completa de todas las tablas relacionadas
```

## Casos de Prueba de Frontera

### Manejo de Valores Nulos

Verificar el comportamiento correcto cuando existen registros con relaciones opcionales.

```sql
-- Prueba 11: Frontera - Préstamo sin agente asignado
-- Objetivo: Verificar manejo de valores nulos en relaciones opcionales
INSERT INTO customer (first_name, last_name, email, id_number)
VALUES ('Sofia', 'Hernández', 'sofia.hernandez@test.com', '44444444');

INSERT INTO loan (customer_id, loan_amount, loan_term_months, interest_rate, status)
VALUES (currval('customer_id_seq'), 8000.00, 12, 0.1400, 'PENDING');

-- El préstamo no tiene agent_id ni branch_id
SELECT l.id, l.loan_amount, a.name as agent_name, b.name as branch_name
FROM loan l
LEFT JOIN agent a ON l.agent_id = a.id
LEFT JOIN branch b ON l.branch_id = b.id
WHERE l.customer_id = currval('customer_id_seq');

-- Resultado esperado: Una fila con agent_name y branch_name como NULL
```

### Verificación de Integridad en Cascada

Configurar eliminación en cascada para verificar que los registros relacionados se eliminan correctamente.

```sql
-- Prueba 12: Frontera - Eliminación en cascada
-- Objetivo: Verificar que al eliminar un cliente se eliminan sus préstamos y pagos
-- Nota: Esta prueba requiere que la foreign key tenga ON DELETE CASCADE

--插入 datos de prueba
INSERT INTO customer (first_name, last_name, email, id_number)
VALUES ('Diego', 'Torres', 'diego.torres@test.com', '55555555');

INSERT INTO loan (customer_id, loan_amount, loan_term_months, interest_rate, status)
VALUES (currval('customer_id_seq'), 12000.00, 24, 0.1700, 'APPROVED');

INSERT INTO payment (loan_id, amount, payment_date, status)
VALUES (currval('loan_id_seq'), 600.00, '2024-01-10', 'COMPLETED');

-- Obtener el ID del cliente插入
SELECT id FROM customer WHERE id_number = '55555555';

-- Eliminar el cliente
DELETE FROM customer WHERE id_number = '55555555';

-- Verificar que se eliminaron los registros relacionados
SELECT COUNT(*) as remaining_records FROM loan 
WHERE customer_id = (el ID obtenido);

-- Resultado esperado: 0 registros en loan (eliminación en cascada)
```

## Resumen de Resultados Esperados

| Prueba | Tipo | Resultado Esperado |
|--------|------|-------------------|
| Integridad referencial | Error | Violación de foreign key al insertar pago con loan_id inexistente |
| Unicidad | Error | Violación de restricción unique al duplicar email |
| 1FN - Pagos | Éxito | Tres pagos independientes para un préstamo |
| 2FN - Dependencias | Éxito | Datos del cliente sin duplicar en tabla loan |
| 3FN - Transitivas | Éxito | Agent y branch en tablas separadas |
| Rendimiento - Cliente | Éxito | Consulta en menos de 50ms |
| Rendimiento - Verificación | Éxito | Consulta en menos de 100ms |
| Rendimiento - Pagos | Éxito | Consulta en menos de 150ms |
| Consistencia - Balance | Éxito | Todos los balances consistentes |
| Consistencia - JOIN | Éxito | Información completa sin nulos inesperados |
| Frontera - Nulos | Éxito | Manejo correcto de valores nulos |
| Frontera - Cascada | Éxito | Eliminación en cascada funciona |