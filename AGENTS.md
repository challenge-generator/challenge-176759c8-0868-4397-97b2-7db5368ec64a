# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Normalización de Modelo de Datos en Sistema de Gestión de Préstamos**.

| | |
|---|---|
| Tema | Diseño e Implementación de un Modelo de Datos Normalizado |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean |
| Tiempo estimado | 10 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web n/a
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.postgresql:postgresql 42.7.3
- com.fasterxml.jackson.core:jackson-databind n/a
- org.projectlombok:lombok 1.18.32
- org.springframework.boot:spring-boot-starter-test n/a
- org.testcontainers:postgresql 1.19.8
- org.hibernate:hibernate-core n/a
- io.micrometer:micrometer-registry-prometheus n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Análisis del Modelo Actual**: Documento que detalla las debilidades y redundancias en el modelo de datos actual.
- **Fase 2 — Diseño del Modelo Normalizado**: Modelo de datos normalizado documentado con entidades, relaciones y atributos.
- **Fase 3 — Implementación y Validación**: Base de datos con el modelo de datos normalizado implementado y pruebas de validación documentadas.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Archivos que la arquitectura declara (1 de 22)

La propuesta arquitectonica del reto los lista y no llegaron al repo. Crealos con implementacion real, respetando la capa en la que viven:

- [ ] `src/main/java/com/pragma/loans/domain/ports/FraudCheckPort.java`

### 2. Referencias colgando (105)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `com.pragma.loans.infrastructure.repositories.JpaFraudCheckRepository`
      El import com.pragma.loans.infrastructure.repositories.JpaFraudCheckRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `com.pragma.loans.infrastructure.repositories.JpaRiskReportRepository`
      El import com.pragma.loans.infrastructure.repositories.JpaRiskReportRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/loans/infrastructure/config/DatabaseConfig.java` — `com.zaxxer.hikari`
      El import com.zaxxer.hikari.HikariConfig pertenece a com.zaxxer.hikari, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/loans/infrastructure/config/DatabaseConfig.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/pragma/loans/LoansApplication.java` — `ApplicationProperties.getCorePoolSize`
      Se invoca `getCorePoolSize` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/LoansApplication.java` — `ApplicationProperties.getMaxPoolSize`
      Se invoca `getMaxPoolSize` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/LoansApplication.java` — `ApplicationProperties.getQueueCapacity`
      Se invoca `getQueueCapacity` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/LoansApplication.java` — `ApplicationProperties.getDb`
      Se invoca `getDb` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `Loan.status`
      Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheckPort.executeFraudCheck`
      Se invoca `executeFraudCheck` sobre `FraudCheckPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheck.status`
      Se invoca `status` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheck.id`
      Se invoca `id` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheck.fraudType`
      Se invoca `fraudType` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheck.fraudScore`
      Se invoca `fraudScore` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `LoanRepositoryPort.update`
      Se invoca `update` sobre `LoanRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `RiskReportPort.generateReport`
      Se invoca `generateReport` sobre `RiskReportPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `RiskReport.id`
      Se invoca `id` sobre `RiskReport`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `Loan.outstandingBalance`
      Se invoca `outstandingBalance` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.save`
      Se invoca `save` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.findById`
      Se invoca `findById` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.findByCustomerId`
      Se invoca `findByCustomerId` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.findByStatus`
      Se invoca `findByStatus` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.findAll`
      Se invoca `findAll` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.existsById`
      Se invoca `existsById` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.deleteById`
      Se invoca `deleteById` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.customer`
      Se invoca `customer` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.id`
      Se invoca `id` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.firstName`
      Se invoca `firstName` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.lastName`
      Se invoca `lastName` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.email`
      Se invoca `email` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.creditScore`
      Se invoca `creditScore` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.createdAt`
      Se invoca `createdAt` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.amount`
      Se invoca `amount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.termMonths`
      Se invoca `termMonths` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.purpose`
      Se invoca `purpose` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.status`
      Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `LoanEntity.setApplicationDate`
      Se invoca `setApplicationDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.applicationDate`
      Se invoca `applicationDate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `LoanEntity.setDueDate`
      Se invoca `setDueDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.dueDate`
      Se invoca `dueDate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.outstandingBalance`
      Se invoca `outstandingBalance` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.fraudCheckId`
      Se invoca `fraudCheckId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.riskReportId`
      Se invoca `riskReportId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `LoanEntity.getApplicationDate`
      Se invoca `getApplicationDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `LoanEntity.getDueDate`
      Se invoca `getDueDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `Loan.customer`
      Se invoca `customer` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.id`
      Se invoca `id` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `Loan.amount`
      Se invoca `amount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.setLoanId`
      Se invoca `setLoanId` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.loanId`
      Se invoca `loanId` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.setCustomerId`
      Se invoca `setCustomerId` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.customerId`
      Se invoca `customerId` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.setCustomerEmail`
      Se invoca `setCustomerEmail` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.customerEmail`
      Se invoca `customerEmail` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.status`
      Se invoca `status` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.reason`
      Se invoca `reason` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.fraudType`
      Se invoca `fraudType` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.fraudScore`
      Se invoca `fraudScore` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.checkedAt`
      Se invoca `checkedAt` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.getLoanId`
      Se invoca `getLoanId` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.getCustomerId`
      Se invoca `getCustomerId` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.getCustomerEmail`
      Se invoca `getCustomerEmail` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setCustomerId`
      Se invoca `setCustomerId` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setBureauName`
      Se invoca `setBureauName` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setRiskCategory`
      Se invoca `setRiskCategory` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setEligible`
      Se invoca `setEligible` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setRequestedAmount`
      Se invoca `setRequestedAmount` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setMaxAllowedAmount`
      Se invoca `setMaxAllowedAmount` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getCustomerId`
      Se invoca `getCustomerId` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getBureauName`
      Se invoca `getBureauName` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getRiskCategory`
      Se invoca `getRiskCategory` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.isEligible`
      Se invoca `isEligible` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getRequestedAmount`
      Se invoca `getRequestedAmount` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getMaxAllowedAmount`
      Se invoca `getMaxAllowedAmount` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Customer.id`
      Se invoca `id` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.status`
      Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.principalAmount`
      Se invoca `principalAmount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `LoanUseCase.findById`
      Se invoca `findById` sobre `LoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `LoanUseCase.calculateOutstandingBalance`
      Se invoca `calculateOutstandingBalance` sobre `LoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.customerId`
      Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.fraudCheckId`
      Se invoca `fraudCheckId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.riskReportId`
      Se invoca `riskReportId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.setCustomerId`
      Se invoca `setCustomerId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.setPrincipalAmount`
      Se invoca `setPrincipalAmount` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.setStartDate`
      Se invoca `setStartDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.setEndDate`
      Se invoca `setEndDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.save`
      Se invoca `save` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `Loan.status`
      Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.getCustomerId`
      Se invoca `getCustomerId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.findById`
      Se invoca `findById` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.findByStatus`
      Se invoca `findByStatus` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.findByCustomerId`
      Se invoca `findByCustomerId` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `Loan.fraudCheckId`
      Se invoca `fraudCheckId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `Loan.riskReportId`
      Se invoca `riskReportId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.findByStartDateBetween`
      Se invoca `findByStartDateBetween` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanJpaAdapter.findByDateRange`
      Se invoca `findByDateRange` sobre `LoanJpaAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (24)

- `pom.xml`
- `src/main/java/com/pragma/loans/LoansApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/pragma/loans/domain/model/Loan.java`
- `src/main/java/com/pragma/loans/domain/model/Customer.java`
- `src/main/java/com/pragma/loans/domain/model/FraudCheck.java`
- `src/main/java/com/pragma/loans/domain/model/RiskReport.java`
- `src/main/java/com/pragma/loans/domain/ports/LoanRepositoryPort.java`
- `src/main/java/com/pragma/loans/domain/ports/RiskReportPort.java`
- `src/main/java/com/pragma/loans/infrastructure/entities/LoanEntity.java`
- `src/main/java/com/pragma/loans/infrastructure/entities/CustomerEntity.java`
- `src/main/java/com/pragma/loans/infrastructure/entities/FraudCheckEntity.java`
- `src/main/java/com/pragma/loans/infrastructure/entities/RiskReportEntity.java`
- `src/main/java/com/pragma/loans/infrastructure/repositories/JpaLoanRepository.java`
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java`
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java`
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java`
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java`
- `src/main/java/com/pragma/loans/infrastructure/config/DatabaseConfig.java`
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java`
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java`
- `docs/modelo_actual.md`
- `docs/modelo_normalizado.md`
- `docs/validacion.md`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/pragma/loans`
- `src/main/java/com/pragma/loans/domain`
- `src/main/java/com/pragma/loans/domain/model`
- `src/main/java/com/pragma/loans/domain/ports`
- `src/main/java/com/pragma/loans/application`
- `src/main/java/com/pragma/loans/application/usecase`
- `src/main/java/com/pragma/loans/infrastructure`
- `src/main/java/com/pragma/loans/infrastructure/adapters`
- `src/main/java/com/pragma/loans/infrastructure/config`
- `src/main/java/com/pragma/loans/infrastructure/entities`
- `src/main/java/com/pragma/loans/infrastructure/repositories`
- `src/main/resources`
- `src/test/java/com/pragma/loans`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Senior
- Brecha que el reto ataca: Implementa un proceso de normalización en una base de datos según las formas normales y ha usado un modelo Entidad-Relación.
- Mision: Candidato con experiencia en Backend Senior con Java.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
