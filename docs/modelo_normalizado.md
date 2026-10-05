# Diseño del Modelo de Datos Normalizado

## Objetivos de Normalización

El objetivo principal es transformar el modelo de datos actual en un esquema que cumpla con la tercera forma normal (3FN) y opcionalmente la forma normal de Boyce-Codd (BCFN), eliminando las redundancias identificadas y estableciendo integridad referencial completa. El diseño debe soportar el procesamiento de 1500 solicitudes por segundo con latencia máxima de 200ms.

## Diagrama Entidad-Relación

```
┌─────────────────┐       ┌─────────────────┐
│    customer     │       │      loan       │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │◄──────│ customer_id (FK)│
│ first_name      │       │ id (PK)         │
│ last_name       │       │ loan_amount     │
│ email           │       │ loan_term       │
│ id_number       │       │ interest_rate   │
│ phone           │       │ purpose         │
│ address         │       │ status          │
│ city            │       │ application_date│
│ country         │       │ approval_date   │
│ credit_score    │       │ disbursement_date│
│ monthly_income  │       │ outstanding_bal │
│ monthly_expenses│       │ agent_id (FK)   │
│ employment_type │       │ branch_id (FK)  │
│ company_name    │       └────────┬────────┘
│ job_title       │                │
│ years_at_job    │       ┌────────┴────────┐
└─────────────────┘       │                 │
                          ▼                 ▼
                  ┌───────────────┐  ┌───────────────┐
                  │ fraud_check   │  │ risk_report   │
                  ├───────────────┤  ├───────────────┤
                  │ id (PK)       │  │ id (PK)       │
                  │ loan_id (FK)  │  │ loan_id (FK)  │
                  │ status        │  │ status        │
                  │ score         │  │ score         │
                  │ reason        │  │ details       │
                  │ check_type    │  │ bureau_name   │
                  │ checked_at    │  │ report_date   │
                  └───────────────┘  └───────────────┘
                          
                          ┌───────────────┐
                          │ payment       │
                          ├───────────────┤
                          │ id (PK)       │
                          │ loan_id (FK)  │
                          │ amount        │
                          │ payment_date  │
                          │ status        │
                          │ payment_type  │
                          └───────────────┘
                          
┌─────────────────┐       ┌─────────────────┐
│      agent      │       │     branch      │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)         │
│ name            │       │ name            │
│ branch_id (FK)  │◄──────│ code            │
└─────────────────┘       │ address         │
                          │ city            │
                          └─────────────────┘
```

## Justificación de Formas Normales

### Primera Forma Normal (1FN)

La primera forma normal requiere que cada columna contenga valores atómicos y que cada fila sea única. En el modelo actual, la tabla loan_application viola esta forma porque el campo payment_history contiene múltiples valores separados por texto. El modelo normalizado corrige esto mediante la tabla payment, donde cada pago es un registro independiente con un solo valor por columna.

La tabla customer también cumple 1FN porque todos sus campos contienen valores simples: first_name es una cadena, email es una dirección, id_number es un identificador único. No existen columnas que contengan múltiples valores ni grupos repetitivos.

### Segunda Forma Normal (2FN)

Para cumplir la segunda forma normal, toda columna no clave debe depender de la clave primaria completa. En el modelo actual, la tabla loan_application tiene una clave primaria compuesta implícita (id), pero campos como customer_first_name dependen solo de customer_id_number, no del id del préstamo. Este es un problema de dependencia parcial.

El modelo normalizado soluciona esto separando las entidades. La tabla customer tiene su propia clave primaria (id) y todos sus atributos dependen completamente de esa clave. La tabla loan tiene una clave primaria compuesta (id) donde customer_id es una clave foránea, y los atributos del préstamo dependen de esa composición. Ningún atributo de loan depende solo de customer_id sin considerar el id del préstamo.

### Tercera Forma Normal (3FN)

La tercera forma normal establece que ninguna columna no clave debe depender transitivamente de la clave primaria. En el modelo actual, la tabla loan_application tiene campos como assigned_agent_name que dependen de assigned_agent_id, el cual a su vez es la clave primaria de la tabla de agentes. Esto crea una dependencia transitiva.

El modelo normalizado elimina estas dependencias creando tablas separadas para agent y branch. Los campos agent_id y branch_id en la tabla loan son claves foráneas que apuntan a las tablas correspondientes. El nombre del agente y de la sucursal se obtienen mediante joins cuando es necesario, garantizando que cada tabla contenga solo atributos que dependan directamente de su clave primaria.

### Forma Normal de Boyce-Codd (BCFN)

La forma normal de Boyce-Codd es una versión más estricta de la 3FN que maneja anomalías cuando existen múltiples claves candidatas. En el modelo actual, la tabla customer podría tener dos claves candidatas: id y id_number. Si un cliente cambiara su número de identificación, todos los registros relacionados deberían actualizarse.

El modelo normalizado garantiza BCFN porque cada tabla tiene una sola clave primaria designada y las claves foráneas establecen relaciones claras sin ambigüedad. No existen tablas con múltiples claves candidatas que generen dependencias funcionales superpuestas.

## Esquema de Tablas Normalizadas

### Tabla customer

```sql
CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    id_number VARCHAR(50) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    city VARCHAR(100),
    country VARCHAR(100),
    credit_score INT,
    monthly_income DECIMAL(15,2),
    monthly_expenses DECIMAL(15,2),
    employment_type VARCHAR(50),
    company_name VARCHAR(200),
    job_title VARCHAR(100),
    years_at_job INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_customer_email ON customer(email);
CREATE INDEX idx_customer_id_number ON customer(id_number);
CREATE INDEX idx_customer_credit_score ON customer(credit_score);
```

### Tabla loan

```sql
CREATE TABLE loan (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customer(id),
    loan_amount DECIMAL(15,2) NOT NULL,
    loan_term_months INT NOT NULL,
    interest_rate DECIMAL(5,4) NOT NULL,
    loan_purpose VARCHAR(100),
    status VARCHAR(50) DEFAULT 'PENDING',
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approval_date TIMESTAMP,
    disbursement_date TIMESTAMP,
    outstanding_balance DECIMAL(15,2),
    agent_id BIGINT REFERENCES agent(id),
    branch_id BIGINT REFERENCES branch(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_loan_customer ON loan(customer_id);
CREATE INDEX idx_loan_status ON loan(status);
CREATE INDEX idx_loan_application_date ON loan(application_date);
CREATE INDEX idx_loan_outstanding_balance ON loan(outstanding_balance);
```

### Tabla fraud_check

```sql
CREATE TABLE fraud_check (
    id BIGSERIAL PRIMARY KEY,
    loan_id BIGINT NOT NULL REFERENCES loan(id),
    status VARCHAR(50) NOT NULL,
    score INT,
    reason TEXT,
    check_type VARCHAR(100),
    checked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_fraud_check_loan ON fraud_check(loan_id);
CREATE INDEX idx_fraud_check_status ON fraud_check(status);
CREATE UNIQUE INDEX idx_fraud_check_loan_unique ON fraud_check(loan_id) 
    WHERE status IN ('APPROVED', 'REJECTED');
```

### Tabla risk_report

```sql
CREATE TABLE risk_report (
    id BIGSERIAL PRIMARY KEY,
    loan_id BIGINT NOT NULL REFERENCES loan(id),
    status VARCHAR(50) NOT NULL,
    score INT,
    details TEXT,
    bureau_name VARCHAR(100),
    report_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_risk_report_loan ON risk_report(loan_id);
CREATE INDEX idx_risk_report_status ON risk_report(status);
CREATE INDEX idx_risk_report_score ON risk_report(score);
```

### Tabla payment

```sql
CREATE TABLE payment (
    id BIGSERIAL PRIMARY KEY,
    loan_id BIGINT NOT NULL REFERENCES loan(id),
    amount DECIMAL(15,2) NOT NULL,
    payment_date TIMESTAMP NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    payment_type VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_payment_loan ON payment(loan_id);
CREATE INDEX idx_payment_date ON payment(payment_date);
CREATE INDEX idx_payment_status ON payment(status);
```

### Tabla agent

```sql
CREATE TABLE agent (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    branch_id BIGINT REFERENCES branch(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_agent_branch ON agent(branch_id);
```

### Tabla branch

```sql
CREATE TABLE branch (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    address TEXT,
    city VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_branch_code ON branch(code);
CREATE INDEX idx_branch_city ON branch(city);
```

## Beneficios del Modelo Normalizado

### Eliminación de Redundancia

Los datos del cliente se almacenan una sola vez en la tabla customer. Múltiples préstamos del mismo cliente referencian el mismo registro mediante la clave foránea customer_id. Esto elimina la duplicación de información personal en cada solicitud de préstamo.

### Integridad Referencial

Las restricciones foreign key garantizan que no puedan existir registros huérfanos. Un pago solo puede existir si el préstamo asociado existe. Un préstamo solo puede estar asociado a un cliente válido. Esta integridad se verifica automáticamente por la base de datos.

### Optimización de Consultas

Los índices en columnas de búsqueda frecuente permiten recuperar información rápidamente. La consulta de préstamos por cliente utiliza el índice idx_loan_customer. La búsqueda por estado de verificación antifraude utiliza idx_fraud_check_status. Estos índices soportan el cumplimiento del SLA de 200ms.

### Flexibilidad para Extensiones

El modelo permite agregar nuevas entidades sin modificar la estructura existente. Para añadir información del empleador, se crearía una tabla employment con clave foránea a customer. Para añadir más detalles de verificación, se agregarían columnas a fraud_check sin impactar otras tablas.