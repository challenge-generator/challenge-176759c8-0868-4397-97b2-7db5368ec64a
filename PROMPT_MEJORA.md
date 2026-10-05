# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Archivos que la arquitectura del reto declara y no estan

Creálos con implementacion real, en la capa que les corresponde:

- `src/main/java/com/pragma/loans/domain/ports/FraudCheckPort.java`

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `com.pragma.loans.infrastructure.repositories.JpaFraudCheckRepository`: El import com.pragma.loans.infrastructure.repositories.JpaFraudCheckRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `com.pragma.loans.infrastructure.repositories.JpaRiskReportRepository`: El import com.pragma.loans.infrastructure.repositories.JpaRiskReportRepository usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/loans/infrastructure/config/DatabaseConfig.java` — `com.zaxxer.hikari`: El import com.zaxxer.hikari.HikariConfig pertenece a com.zaxxer.hikari, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/loans/infrastructure/config/DatabaseConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/pragma/loans/LoansApplication.java` — `ApplicationProperties.getCorePoolSize`: Se invoca `getCorePoolSize` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/LoansApplication.java` — `ApplicationProperties.getMaxPoolSize`: Se invoca `getMaxPoolSize` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/LoansApplication.java` — `ApplicationProperties.getQueueCapacity`: Se invoca `getQueueCapacity` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/LoansApplication.java` — `ApplicationProperties.getDb`: Se invoca `getDb` sobre `ApplicationProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `Loan.status`: Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheckPort.executeFraudCheck`: Se invoca `executeFraudCheck` sobre `FraudCheckPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheck.status`: Se invoca `status` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheck.id`: Se invoca `id` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheck.fraudType`: Se invoca `fraudType` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `FraudCheck.fraudScore`: Se invoca `fraudScore` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `LoanRepositoryPort.update`: Se invoca `update` sobre `LoanRepositoryPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `RiskReportPort.generateReport`: Se invoca `generateReport` sobre `RiskReportPort`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `RiskReport.id`: Se invoca `id` sobre `RiskReport`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java` — `Loan.outstandingBalance`: Se invoca `outstandingBalance` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.save`: Se invoca `save` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.findById`: Se invoca `findById` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.findByCustomerId`: Se invoca `findByCustomerId` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.findByStatus`: Se invoca `findByStatus` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.findAll`: Se invoca `findAll` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.existsById`: Se invoca `existsById` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `JpaLoanRepository.deleteById`: Se invoca `deleteById` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.customer`: Se invoca `customer` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.id`: Se invoca `id` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.firstName`: Se invoca `firstName` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.lastName`: Se invoca `lastName` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.email`: Se invoca `email` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.creditScore`: Se invoca `creditScore` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Customer.createdAt`: Se invoca `createdAt` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.amount`: Se invoca `amount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.termMonths`: Se invoca `termMonths` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.purpose`: Se invoca `purpose` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.status`: Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `LoanEntity.setApplicationDate`: Se invoca `setApplicationDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.applicationDate`: Se invoca `applicationDate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `LoanEntity.setDueDate`: Se invoca `setDueDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.dueDate`: Se invoca `dueDate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.outstandingBalance`: Se invoca `outstandingBalance` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.fraudCheckId`: Se invoca `fraudCheckId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `Loan.riskReportId`: Se invoca `riskReportId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `LoanEntity.getApplicationDate`: Se invoca `getApplicationDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java` — `LoanEntity.getDueDate`: Se invoca `getDueDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `Loan.customer`: Se invoca `customer` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.id`: Se invoca `id` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `Loan.amount`: Se invoca `amount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.setLoanId`: Se invoca `setLoanId` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.loanId`: Se invoca `loanId` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.setCustomerId`: Se invoca `setCustomerId` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.customerId`: Se invoca `customerId` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.setCustomerEmail`: Se invoca `setCustomerEmail` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.customerEmail`: Se invoca `customerEmail` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.status`: Se invoca `status` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.reason`: Se invoca `reason` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.fraudType`: Se invoca `fraudType` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.fraudScore`: Se invoca `fraudScore` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheck.checkedAt`: Se invoca `checkedAt` sobre `FraudCheck`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.getLoanId`: Se invoca `getLoanId` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.getCustomerId`: Se invoca `getCustomerId` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java` — `FraudCheckEntity.getCustomerEmail`: Se invoca `getCustomerEmail` sobre `FraudCheckEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setCustomerId`: Se invoca `setCustomerId` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setBureauName`: Se invoca `setBureauName` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setRiskCategory`: Se invoca `setRiskCategory` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setEligible`: Se invoca `setEligible` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setRequestedAmount`: Se invoca `setRequestedAmount` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.setMaxAllowedAmount`: Se invoca `setMaxAllowedAmount` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getCustomerId`: Se invoca `getCustomerId` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getBureauName`: Se invoca `getBureauName` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getRiskCategory`: Se invoca `getRiskCategory` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.isEligible`: Se invoca `isEligible` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getRequestedAmount`: Se invoca `getRequestedAmount` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java` — `RiskReportEntity.getMaxAllowedAmount`: Se invoca `getMaxAllowedAmount` sobre `RiskReportEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Customer.id`: Se invoca `id` sobre `Customer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.status`: Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.principalAmount`: Se invoca `principalAmount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `LoanUseCase.findById`: Se invoca `findById` sobre `LoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `LoanUseCase.calculateOutstandingBalance`: Se invoca `calculateOutstandingBalance` sobre `LoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.customerId`: Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.fraudCheckId`: Se invoca `fraudCheckId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java` — `Loan.riskReportId`: Se invoca `riskReportId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.setCustomerId`: Se invoca `setCustomerId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.setPrincipalAmount`: Se invoca `setPrincipalAmount` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.setStartDate`: Se invoca `setStartDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.setEndDate`: Se invoca `setEndDate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.save`: Se invoca `save` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `Loan.status`: Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanEntity.getCustomerId`: Se invoca `getCustomerId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.findById`: Se invoca `findById` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.findByStatus`: Se invoca `findByStatus` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.findByCustomerId`: Se invoca `findByCustomerId` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `Loan.fraudCheckId`: Se invoca `fraudCheckId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `Loan.riskReportId`: Se invoca `riskReportId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `JpaLoanRepository.findByStartDateBetween`: Se invoca `findByStartDateBetween` sobre `JpaLoanRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java` — `LoanJpaAdapter.findByDateRange`: Se invoca `findByDateRange` sobre `LoanJpaAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Senior

### Brecha de conocimiento
Implementa un proceso de normalización en una base de datos según las formas normales y ha usado un modelo Entidad-Relación.

### Misión / candidato
Candidato con experiencia en Backend Senior con Java.

### Reto
- Tema: Diseño e Implementación de un Modelo de Datos Normalizado
- Seniority: senior-l2
- Tipo: practical
- Título: Normalización de Modelo de Datos en Sistema de Gestión de Préstamos
- Tiempo estimado: 10 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Análisis del Modelo Actual — objetivo: Identificar las debilidades y redundancias en el modelo de datos actual. — entregable (NO resolver): Documento que detalla las debilidades y redundancias en el modelo de datos actual.
- Fase 2: Diseño del Modelo Normalizado — objetivo: Crear un modelo de datos normalizado que elimine las redundancias y mejore la consistencia. — entregable (NO resolver): Modelo de datos normalizado documentado con entidades, relaciones y atributos.
- Fase 3: Implementación y Validación — objetivo: Implementar el modelo de datos normalizado y validar su funcionamiento. — entregable (NO resolver): Base de datos con el modelo de datos normalizado implementado y pruebas de validación documentadas.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.pragma</groupId>
    <artifactId>loans</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>loans</name>
    <description>Sistema de gestión de préstamos con modelo de datos normalizado</description>

    <properties>
        <java.version>21</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <testcontainers.version>1.19.8</testcontainers.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.3</version>
        </dependency>

        <!-- Monitoring -->
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-prometheus</artifactId>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.32</version>
            <scope>provided</scope>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <version>${testcontainers.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${testcontainers.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
        </repository>
    </repositories>
</project>

// === ARCHIVO: src/main/java/com/pragma/loans/LoansApplication.java ===
package com.pragma.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableConfigurationProperties
public class LoansApplication {
    private final ApplicationProperties applicationProperties;

    public LoansApplication(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        validateProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
    }

    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(applicationProperties.getCorePoolSize());
        executor.setMaxPoolSize(applicationProperties.getMaxPoolSize());
        executor.setQueueCapacity(applicationProperties.getQueueCapacity());
        executor.setThreadNamePrefix("loan-async-");
        executor.initialize();
        return executor;
    }

    private void validateProperties() {
        if (applicationProperties.getCorePoolSize() <= 0) {
            throw new IllegalStateException("corePoolSize debe ser mayor que 0");
        }
        if (applicationProperties.getMaxPoolSize() < applicationProperties.getCorePoolSize()) {
            throw new IllegalStateException("maxPoolSize debe ser mayor o igual que corePoolSize");
        }
        if (applicationProperties.getQueueCapacity() <= 0) {
            throw new IllegalStateException("queueCapacity debe ser mayor que 0");
        }
        if (applicationProperties.getDb().getUrl() == null || applicationProperties.getDb().getUrl().isBlank()) {
            throw new IllegalStateException("La URL de la base de datos no puede estar vacía");
        }
    }
}

record ApplicationProperties(
    DbProperties db,
    int corePoolSize,
    int maxPoolSize,
    int queueCapacity
) {}

record DbProperties(
    String url,
    String username,
    String password,
    String driverClassName,
    int maxPoolSize,
    int minIdle,
    long connectionTimeout,
    long idleTimeout,
    long maxLifetime
) {}

// === ARCHIVO: src/main/resources/application.yml ===
server:
  port: 8080
  servlet:
    context-path: /api/loans
  tomcat:
    threads:
      max: 200
    connection-timeout: 20000

spring:
  application:
    name: loans-service
  datasource:
    url: jdbc:postgresql://localhost:5432/loans_db
    username: loans_user
    password: loans_pass
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
      pool-name: LoansHikariPool
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        jdbc:
          batch_size: 50
        order_inserts: true
        order_updates: true
        generate_statistics: true
        format_sql: true
    show-sql: true
    open-in-view: false

management:
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    tags:
      application: ${spring.application.name}

logging:
  level:
    root: INFO
    org.springframework.web: INFO
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
    com.pragma.loans: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

application:
  async:
    core-pool-size: 50
    max-pool-size: 100
    queue-capacity: 1000

// === ARCHIVO: src/main/java/com/pragma/loans/domain/model/Loan.java ===
package com.pragma.loans.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record Loan(
    @NotNull
    UUID loanId,
    
    @NotNull
    UUID customerId,
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal amount,
    
    @NotNull
    @Positive
    Integer termMonths,
    
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal interestRate,
    
    @NotNull
    LocalDate startDate,
    
    @NotNull
    @Size(min = 3, max = 20)
    String status,
    
    @NotNull
    LocalDate createdAt,
    
    @NotNull
    LocalDate updatedAt,
    
    @NotNull
    String loanPurpose,
    
    @DecimalMin(value = "0.0")
    BigDecimal outstandingBalance,
    
    UUID fraudCheckId,
    
    UUID riskReportId
) {
    public Loan {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Loan amount must be positive");
        }
        if (termMonths <= 0) {
            throw new IllegalArgumentException("Term months must be positive");
        }
        if (interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Interest rate must be positive");
        }
        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the future");
        }
        if (createdAt.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Created date cannot be in the future");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Updated date cannot be before created date");
        }
        if (outstandingBalance.compareTo(amount) > 0) {
            throw new IllegalArgumentException("Outstanding balance cannot exceed loan amount");
        }
    }
    
    public Loan withStatus(String newStatus) {
        return new Loan(
            this.loanId,
            this.customerId,
            this.amount,
            this.termMonths,
            this.interestRate,
            this.startDate,
            newStatus,
            this.createdAt,
            LocalDate.now(),
            this.loanPurpose,
            this.outstandingBalance,
            this.fraudCheckId,
            this.riskReportId
        );
    }
    
    public Loan withFraudCheckId(UUID fraudCheckId) {
        return new Loan(
            this.loanId,
            this.customerId,
            this.amount,
            this.termMonths,
            this.interestRate,
            this.startDate,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.loanPurpose,
            this.outstandingBalance,
            fraudCheckId,
            this.riskReportId
        );
    }
    
    public Loan withRiskReportId(UUID riskReportId) {
        return new Loan(
            this.loanId,
            this.customerId,
            this.amount,
            this.termMonths,
            this.interestRate,
            this.startDate,
            this.status,
            this.createdAt,
            this.updatedAt,
            this.loanPurpose,
            this.outstandingBalance,
            this.fraudCheckId,
            riskReportId
        );
    }
    
    public Loan withOutstandingBalance(BigDecimal newBalance) {
        if (newBalance.compareTo(amount) > 0) {
            throw new IllegalArgumentException("Outstanding balance cannot exceed loan amount");
        }
        return new Loan(
            this.loanId,
            this.customerId,
            this.amount,
            this.termMonths,
            this.interestRate,
            this.startDate,
            this.status,
            this.createdAt,
            LocalDate.now(),
            this.loanPurpose,
            newBalance,
            this.fraudCheckId,
            this.riskReportId
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/domain/model/Customer.java ===
package com.pragma.loans.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Customer(
    @NotNull
    UUID customerId,
    
    @NotNull
    @Size(min = 2, max = 100)
    String firstName,
    
    @NotNull
    @Size(min = 2, max = 100)
    String lastName,
    
    @NotNull
    @Email
    String email,
    
    @NotNull
    @Pattern(regexp = "^[0-9]{10,15}$")
    String phoneNumber,
    
    @NotNull
    @Size(min = 10, max = 200)
    String address,
    
    @NotNull
    @Past
    LocalDate dateOfBirth,
    
    @NotNull
    @Size(min = 3, max = 20)
    String identificationType,
    
    @NotNull
    @Size(min = 5, max = 30)
    String identificationNumber,
    
    @NotNull
    LocalDate createdAt,
    
    @NotNull
    LocalDate updatedAt,
    
    @NotNull
    String employmentStatus,
    
    @NotNull
    Integer creditScore
) {
    public Customer {
        if (dateOfBirth.isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("Customer must be at least 18 years old");
        }
        if (createdAt.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Created date cannot be in the future");
        }
        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("Updated date cannot be before created date");
        }
        if (creditScore < 300 || creditScore > 850) {
            throw new IllegalArgumentException("Credit score must be between 300 and 850");
        }
    }
    
    public Customer withUpdatedInfo(String newFirstName, String newLastName, String newEmail,
                                   String newPhoneNumber, String newAddress, String newEmploymentStatus) {
        return new Customer(
            this.customerId,
            newFirstName,
            newLastName,
            newEmail,
            newPhoneNumber,
            newAddress,
            this.dateOfBirth,
            this.identificationType,
            this.identificationNumber,
            this.createdAt,
            LocalDate.now(),
            newEmploymentStatus,
            this.creditScore
        );
    }
    
    public Customer withCreditScore(int newCreditScore) {
        if (newCreditScore < 300 || newCreditScore > 850) {
            throw new IllegalArgumentException("Credit score must be between 300 and 850");
        }
        return new Customer(
            this.customerId,
            this.firstName,
            this.lastName,
            this.email,
            this.phoneNumber,
            this.address,
            this.dateOfBirth,
            this.identificationType,
            this.identificationNumber,
            this.createdAt,
            LocalDate.now(),
            this.employmentStatus,
            newCreditScore
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/domain/model/FraudCheck.java ===
package com.pragma.loans.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public record FraudCheck(
    @NotNull
    UUID fraudCheckId,
    
    @NotNull
    UUID loanId,
    
    @NotNull
    @Size(min = 3, max = 20)
    String status,
    
    @NotNull
    @PastOrPresent
    LocalDateTime checkDate,
    
    @NotNull
    @Size(max = 500)
    String reason,
    
    @NotNull
    Boolean isFlagged,
    
    @Size(max = 200)
    String fraudType,
    
    @NotNull
    Integer fraudScore
) {
    public FraudCheck {
        if (fraudScore < 0 || fraudScore > 1000) {
            throw new IllegalArgumentException("Fraud score must be between 0 and 1000");
        }
        if (checkDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Check date cannot be in the future");
        }
    }
    
    public FraudCheck withStatus(String newStatus, String newReason) {
        return new FraudCheck(
            this.fraudCheckId,
            this.loanId,
            newStatus,
            LocalDateTime.now(),
            newReason,
            this.isFlagged,
            this.fraudType,
            this.fraudScore
        );
    }
    
    public FraudCheck withFraudDetails(String fraudType, int fraudScore) {
        if (fraudScore < 0 || fraudScore > 1000) {
            throw new IllegalArgumentException("Fraud score must be between 0 and 1000");
        }
        return new FraudCheck(
            this.fraudCheckId,
            this.loanId,
            this.status,
            this.checkDate,
            this.reason,
            fraudScore > 700,
            fraudType,
            fraudScore
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/domain/model/RiskReport.java ===
package com.pragma.loans.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record RiskReport(
    UUID id,
    UUID loanApplicationId,
    String bureauCode,
    int riskScore,
    String riskLevel,
    BigDecimal creditExposure,
    int delinquencyHistory,
    int totalAccounts,
    int activeAccounts,
    int closedAccounts,
    BigDecimal totalOutstanding,
    BigDecimal totalPastDue,
    int inquiriesLast6Months,
    String legalActionsFlag,
    String collectionsFlag,
    String bankruptcyFlag,
    LocalDateTime reportDate,
    String dataSource,
    String verificationStatus,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public RiskReport withRiskScore(int newRiskScore) {
        return new RiskReport(
            this.id,
            this.loanApplicationId,
            this.bureauCode,
            newRiskScore,
            this.riskLevel,
            this.creditExposure,
            this.delinquencyHistory,
            this.totalAccounts,
            this.activeAccounts,
            this.closedAccounts,
            this.totalOutstanding,
            this.totalPastDue,
            this.inquiriesLast6Months,
            this.legalActionsFlag,
            this.collectionsFlag,
            this.bankruptcyFlag,
            this.reportDate,
            this.dataSource,
            this.verificationStatus,
            this.createdAt,
            this.updatedAt
        );
    }

    public RiskReport withVerificationStatus(String newStatus) {
        return new RiskReport(
            this.id,
            this.loanApplicationId,
            this.bureauCode,
            this.riskScore,
            this.riskLevel,
            this.creditExposure,
            this.delinquencyHistory,
            this.totalAccounts,
            this.activeAccounts,
            this.closedAccounts,
            this.totalOutstanding,
            this.totalPastDue,
            this.inquiriesLast6Months,
            this.legalActionsFlag,
            this.collectionsFlag,
            this.bankruptcyFlag,
            this.reportDate,
            this.dataSource,
            newStatus,
            this.createdAt,
            LocalDateTime.now()
        );
    }

    public RiskReport withRiskLevel(String newRiskLevel) {
        return new RiskReport(
            this.id,
            this.loanApplicationId,
            this.bureauCode,
            this.riskScore,
            newRiskLevel,
            this.creditExposure,
            this.delinquencyHistory,
            this.totalAccounts,
            this.activeAccounts,
            this.closedAccounts,
            this.totalOutstanding,
            this.totalPastDue,
            this.inquiriesLast6Months,
            this.legalActionsFlag,
            this.collectionsFlag,
            this.bankruptcyFlag,
            this.reportDate,
            this.dataSource,
            this.verificationStatus,
            this.createdAt,
            this.updatedAt
        );
    }

    public boolean isHighRisk() {
        return "HIGH".equalsIgnoreCase(this.riskLevel) || this.riskScore >= 700;
    }

    public boolean hasLegalIssues() {
        return "Y".equalsIgnoreCase(this.legalActionsFlag) || 
               "Y".equalsIgnoreCase(this.collectionsFlag) ||
               "Y".equalsIgnoreCase(this.bankruptcyFlag);
    }

    public boolean isDelinquent() {
        return this.delinquencyHistory > 0 || 
               (this.totalPastDue != null && this.totalPastDue.compareTo(BigDecimal.ZERO) > 0);
    }

    public static RiskReport create(UUID loanApplicationId, String bureauCode) {
        return new RiskReport(
            UUID.randomUUID(),
            loanApplicationId,
            bureauCode,
            0,
            "PENDING",
            BigDecimal.ZERO,
            0,
            0,
            0,
            0,
            BigDecimal.ZERO,
            BigDecimal.ZERO,
            0,
            "N",
            "N",
            "N",
            LocalDateTime.now(),
            bureauCode,
            "PENDING",
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/domain/ports/LoanRepositoryPort.java ===
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

// === ARCHIVO: src/main/java/com/pragma/loans/domain/ports/RiskReportPort.java ===
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

// === ARCHIVO: src/main/java/com/pragma/loans/infrastructure/entities/LoanEntity.java ===
package com.pragma.loans.infrastructure.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad JPA que mapea la tabla de préstamos en el modelo normalizado.
 * Representa una solicitud de préstamo registrada en el sistema.
 */
@Entity
@Table(name = "loan_application", indexes = {
    @Index(name = "idx_loan_customer_id", columnList = "customer_id"),
    @Index(name = "idx_loan_status", columnList = "status"),
    @Index(name = "idx_loan_created_at", columnList = "created_at"),
    @Index(name = "idx_loan_fraud_check_id", columnList = "fraud_check_id"),
    @Index(name = "idx_loan_risk_report_id", columnList = "risk_report_id")
})
public class LoanEntity {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal interestRate;

    @Column(name = "purpose", length = 500)
    private String purpose;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "outstanding_balance", precision = 19, scale = 4)
    private BigDecimal outstandingBalance;

    @Column(name = "monthly_payment", precision = 19, scale = 4)
    private BigDecimal monthlyPayment;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "disbursed_at")
    private LocalDateTime disbursedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "fk_loan_customer"))
    private CustomerEntity customer;

    @Column(name = "fraud_check_id", columnDefinition = "UUID")
    private UUID fraudCheckId;

    @Column(name = "risk_report_id", columnDefinition = "UUID")
    private UUID riskReportId;

    @Column(name = "rejection_reason", length = 1000)
    private String rejectionReason;

    @Column(name = "notes", length = 2000)
    private String notes;

    @Version
    @Column(name = "version")
    private Long version;

    public LoanEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(BigDecimal outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public LocalDateTime getDisbursedAt() {
        return disbursedAt;
    }

    public void setDisbursedAt(LocalDateTime disbursedAt) {
        this.disbursedAt = disbursedAt;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public UUID getFraudCheckId() {
        return fraudCheckId;
    }

    public void setFraudCheckId(UUID fraudCheckId) {
        this.fraudCheckId = fraudCheckId;
    }

    public UUID getRiskReportId() {
        return riskReportId;
    }

    public void setRiskReportId(UUID riskReportId) {
        this.riskReportId = riskReportId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/infrastructure/entities/CustomerEntity.java ===
package com.pragma.loans.infrastructure.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad JPA que mapea la tabla de clientes en el modelo normalizado.
 * Almacena la información personal y crediticia de cada cliente.
 */
@Entity
@Table(name = "customer", indexes = {
    @Index(name = "idx_customer_email", columnList = "email", unique = true),
    @Index(name = "idx_customer_identification", columnList = "identification_number", unique = true),
    @Index(name = "idx_customer_credit_score", columnList = "credit_score"),
    @Index(name = "idx_customer_status", columnList = "status")
})
public class CustomerEntity {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "identification_type", nullable = false, length = 50)
    private String identificationType;

    @Column(name = "identification_number", nullable = false, length = 50)
    private String identificationNumber;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "occupation", length = 150)
    private String occupation;

    @Column(name = "employer_name", length = 200)
    private String employerName;

    @Column(name = "monthly_income", precision = 19, scale = 4)
    private java.math.BigDecimal monthlyIncome;

    @Column(name = "credit_score")
    private Integer creditScore;

    @Column(name = "risk_category", length = 50)
    private String riskCategory;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_loan_date")
    private LocalDateTime lastLoanDate;

    @Column(name = "total_loans_count")
    private Integer totalLoansCount;

    @Column(name = "active_loans_count")
    private Integer activeLoansCount;

    @Column(name = "delinquent_loans_count")
    private Integer delinquentLoansCount;

    @Column(name = "notes", length = 2000)
    private String notes;

    @Version
    @Column(name = "version")
    private Long version;

    public CustomerEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public java.math.BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(java.math.BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getLastLoanDate() {
        return lastLoanDate;
    }

    public void setLastLoanDate(LocalDateTime lastLoanDate) {
        this.lastLoanDate = lastLoanDate;
    }

    public Integer getTotalLoansCount() {
        return totalLoansCount;
    }

    public void setTotalLoansCount(Integer totalLoansCount) {
        this.totalLoansCount = totalLoansCount;
    }

    public Integer getActiveLoansCount() {
        return activeLoansCount;
    }

    public void setActiveLoansCount(Integer activeLoansCount) {
        this.activeLoansCount = activeLoansCount;
    }

    public Integer getDelinquentLoansCount() {
        return delinquentLoansCount;
    }

    public void setDelinquentLoansCount(Integer delinquentLoansCount) {
        this.delinquentLoansCount = delinquentLoansCount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (totalLoansCount == null) {
            totalLoansCount = 0;
        }
        if (activeLoansCount == null) {
            activeLoansCount = 0;
        }
        if (delinquentLoansCount == null) {
            delinquentLoansCount = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/infrastructure/entities/FraudCheckEntity.java ===
package com.pragma.loans.infrastructure.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fraud_check", indexes = {
    @Index(name = "idx_fraud_check_loan_application", columnList = "loan_application_id"),
    @Index(name = "idx_fraud_check_status", columnList = "status"),
    @Index(name = "idx_fraud_check_created_at", columnList = "created_at")
})
public class FraudCheckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "loan_application_id", nullable = false)
    private UUID loanApplicationId;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(length = 500)
    private String reason;

    @Column(name = "fraud_type", length = 100)
    private String fraudType;

    @Column(name = "fraud_score")
    private Integer fraudScore;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public FraudCheckEntity() {
    }

    public FraudCheckEntity(UUID loanApplicationId, String status, String reason,
                           String fraudType, Integer fraudScore) {
        this.loanApplicationId = loanApplicationId;
        this.status = status;
        this.reason = reason;
        this.fraudType = fraudType;
        this.fraudScore = fraudScore;
        this.createdAt = LocalDateTime.now();
        this.checkedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(UUID loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFraudType() {
        return fraudType;
    }

    public void setFraudType(String fraudType) {
        this.fraudType = fraudType;
    }

    public Integer getFraudScore() {
        return fraudScore;
    }

    public void setFraudScore(Integer fraudScore) {
        this.fraudScore = fraudScore;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/infrastructure/entities/RiskReportEntity.java ===
package com.pragma.loans.infrastructure.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "risk_report", indexes = {
    @Index(name = "idx_risk_report_loan_application", columnList = "loan_application_id"),
    @Index(name = "idx_risk_report_risk_level", columnList = "risk_level"),
    @Index(name = "idx_risk_report_created_at", columnList = "created_at")
})
public class RiskReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "loan_application_id", nullable = false)
    private UUID loanApplicationId;

    @Column(name = "risk_level", nullable = false, length = 50)
    private String riskLevel;

    @Column(name = "risk_score")
    private Integer riskScore;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(name = "risk_factors", columnDefinition = "TEXT")
    private String riskFactors;

    @Column(name = "recommendations", columnDefinition = "TEXT")
    private String recommendations;

    @Column(name = "analyst_name", length = 200)
    private String analystName;

    @Column(name = "report_date")
    private LocalDateTime reportDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public RiskReportEntity() {
    }

    public RiskReportEntity(UUID loanApplicationId, String riskLevel, Integer riskScore,
                           String details, String riskFactors, String recommendations) {
        this.loanApplicationId = loanApplicationId;
        this.riskLevel = riskLevel;
        this.riskScore = riskScore;
        this.details = details;
        this.riskFactors = riskFactors;
        this.recommendations = recommendations;
        this.createdAt = LocalDateTime.now();
        this.reportDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(UUID loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRiskFactors() {
        return riskFactors;
    }

    public void setRiskFactors(String riskFactors) {
        this.riskFactors = riskFactors;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getAnalystName() {
        return analystName;
    }

    public void setAnalystName(String analystName) {
        this.analystName = analystName;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/infrastructure/repositories/JpaLoanRepository.java ===
package com.pragma.loans.infrastructure.repositories;

import com.pragma.loans.infrastructure.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaLoanRepository extends JpaRepository<LoanEntity, UUID> {

    @Query("SELECT l FROM LoanEntity l WHERE l.customerId = :customerId AND l.status = :status")
    List<LoanEntity> findByCustomerIdAndStatus(@Param("customerId") UUID customerId, 
                                                @Param("status") String status);

    @Query("SELECT l FROM LoanEntity l WHERE l.status = :status AND l.createdAt BETWEEN :startDate AND :endDate")
    List<LoanEntity> findByStatusAndDateRange(@Param("status") String status,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    @Query("SELECT l FROM LoanEntity l WHERE l.outstandingBalance > :minBalance")
    List<LoanEntity> findByOutstandingBalanceGreaterThan(@Param("minBalance") BigDecimal minBalance);

    @Query("SELECT l FROM LoanEntity l WHERE l.customerId = :customerId ORDER BY l.createdAt DESC")
    List<LoanEntity> findByCustomerIdOrderByCreatedAtDesc(@Param("customerId") UUID customerId);

    @Query("SELECT COUNT(l) FROM LoanEntity l WHERE l.status = :status")
    long countByStatus(@Param("status") String status);

    @Query("SELECT l FROM LoanEntity l WHERE l.fraudCheckId IS NOT NULL AND l.riskReportId IS NULL")
    List<LoanEntity> findPendingRiskAssessment();

    @Query("SELECT l FROM LoanEntity l WHERE l.status = 'APPROVED' AND l.outstandingBalance > 0")
    List<LoanEntity> findActiveLoans();

    Optional<LoanEntity> findByLoanNumber(String loanNumber);
}

// === ARCHIVO: src/main/java/com/pragma/loans/application/usecase/LoanUseCase.java ===
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

// === ARCHIVO: src/main/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapter.java ===
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

// === ARCHIVO: src/main/java/com/pragma/loans/infrastructure/adapters/FraudCheckAdapter.java ===
package com.pragma.loans.infrastructure.adapters;

import com.pragma.loans.domain.model.FraudCheck;
import com.pragma.loans.domain.model.Loan;
import com.pragma.loans.domain.ports.FraudCheckPort;
import com.pragma.loans.infrastructure.entities.FraudCheckEntity;
import com.pragma.loans.infrastructure.repositories.JpaFraudCheckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FraudCheckAdapter implements FraudCheckPort {

    private static final Logger log = LoggerFactory.getLogger(FraudCheckAdapter.class);
    private static final String STATUS_APPROVED = "APPROVED";
    private static final String STATUS_REJECTED = "REJECTED";
    private static final String FRAUD_TYPE_IDENTITY = "IDENTITY_FRAUD";
    private static final String FRAUD_TYPE_APPLICATION = "APPLICATION_FRAUD";
    private static final String FRAUD_TYPE_NONE = "NONE";

    private final JpaFraudCheckRepository fraudCheckRepository;

    public FraudCheckAdapter(JpaFraudCheckRepository fraudCheckRepository) {
        this.fraudCheckRepository = fraudCheckRepository;
    }

    @Override
    public FraudCheck executeFraudCheck(Loan loan) {
        log.info("Ejecutando verificación antifraude para préstamo: {}", loan.id());

        String customerEmail = loan.customer() != null ? loan.customer().email() : "";
        String customerId = loan.customer() != null ? loan.customer().id().toString() : "";

        boolean flagged = shouldFlagForFraud(loan);

        FraudCheck fraudCheck;
        if (flagged) {
            String fraudType = determineFraudType(loan);
            int fraudScore = calculateFraudScore(loan, fraudType);

            fraudCheck = new FraudCheck(
                UUID.randomUUID(),
                loan.id(),
                customerId,
                customerEmail,
                STATUS_REJECTED,
                "Application flagged by fraud detection engine",
                fraudType,
                fraudScore,
                LocalDateTime.now()
            );

            log.warn("Verificación antifraude rechazada para préstamo {}: tipo={}, score={}",
                loan.id(), fraudType, fraudScore);
        } else {
            fraudCheck = new FraudCheck(
                UUID.randomUUID(),
                loan.id(),
                customerId,
                customerEmail,
                STATUS_APPROVED,
                "No fraud indicators detected",
                FRAUD_TYPE_NONE,
                0,
                LocalDateTime.now()
            );

            log.info("Verificación antifraude aprobada para préstamo: {}", loan.id());
        }

        return fraudCheck;
    }

    @Override
    public FraudCheck save(FraudCheck fraudCheck) {
        log.debug("Guardando verificación antifraude: {}", fraudCheck.id());
        FraudCheckEntity entity = toEntity(fraudCheck);
        FraudCheckEntity savedEntity = fraudCheckRepository.save(entity);
        log.info("Verificación antifraude {} guardada con estado: {}",
            savedEntity.getId(), savedEntity.getStatus());
        return toDomain(savedEntity);
    }

    @Override
    public Optional<FraudCheck> findById(UUID id) {
        log.debug("Buscando verificación antifraude por ID: {}", id);
        return fraudCheckRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<FraudCheck> findByLoanId(UUID loanId) {
        log.debug("Buscando verificación antifraude para préstamo: {}", loanId);
        return fraudCheckRepository.findByLoanId(loanId).map(this::toDomain);
    }

    private boolean shouldFlagForFraud(Loan loan) {
        if (loan.customer() == null) {
            return true;
        }

        if (loan.amount() != null && loan.amount().compareTo(java.math.BigDecimal.valueOf(100000)) > 0) {
            log.debug("Monto alto detectado: {}", loan.amount());
        }

        if (loan.customer().creditScore() < 500) {
            log.debug("Score de crédito bajo: {}", loan.customer().creditScore());
            return true;
        }

        String email = loan.customer().email();
        if (email != null && (email.contains("test") || email.contains("fake") || email.contains("invalid"))) {
            log.debug("Email sospechoso: {}", email);
            return true;
        }

        return false;
    }

    private String determineFraudType(Loan loan) {
        if (loan.customer() != null && loan.customer().creditScore() < 500) {
            return FRAUD_TYPE_IDENTITY;
        }

        String email = loan.customer() != null ? loan.customer().email() : "";
        if (email.contains("test") || email.contains("fake")) {
            return FRAUD_TYPE_APPLICATION;
        }

        return FRAUD_TYPE_NONE;
    }

    private int calculateFraudScore(Loan loan, String fraudType) {
        int baseScore = 0;

        if (FRAUD_TYPE_IDENTITY.equals(fraudType)) {
            baseScore = 75;
        } else if (FRAUD_TYPE_APPLICATION.equals(fraudType)) {
            baseScore = 60;
        }

        if (loan.amount() != null && loan.amount().compareTo(java.math.BigDecimal.valueOf(50000)) > 0) {
            baseScore += 10;
        }

        if (loan.customer() != null && loan.customer().creditScore() < 400) {
            baseScore += 15;
        }

        return Math.min(baseScore, 100);
    }

    private FraudCheckEntity toEntity(FraudCheck fraudCheck) {
        FraudCheckEntity entity = new FraudCheckEntity();
        entity.setId(fraudCheck.id());
        entity.setLoanId(fraudCheck.loanId());
        entity.setCustomerId(fraudCheck.customerId());
        entity.setCustomerEmail(fraudCheck.customerEmail());
        entity.setStatus(fraudCheck.status());
        entity.setReason(fraudCheck.reason());
        entity.setFraudType(fraudCheck.fraudType());
        entity.setFraudScore(fraudCheck.fraudScore());
        entity.setCheckedAt(fraudCheck.checkedAt());
        return entity;
    }

    private FraudCheck toDomain(FraudCheckEntity entity) {
        return new FraudCheck(
            entity.getId(),
            entity.getLoanId(),
            entity.getCustomerId(),
            entity.getCustomerEmail(),
            entity.getStatus(),
            entity.getReason(),
            entity.getFraudType(),
            entity.getFraudScore(),
            entity.getCheckedAt()
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/infrastructure/adapters/RiskReportAdapter.java ===
package com.pragma.loans.infrastructure.adapters;

import com.pragma.loans.domain.model.RiskReport;
import com.pragma.loans.domain.ports.RiskReportPort;
import com.pragma.loans.infrastructure.entities.RiskReportEntity;
import com.pragma.loans.infrastructure.repositories.JpaRiskReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class RiskReportAdapter implements RiskReportPort {

    private static final Logger log = LoggerFactory.getLogger(RiskReportAdapter.class);
    private static final String BUREAU_NAME = "CREDIT_BUREAU";
    private static final int DEFAULT_RISK_SCORE = 500;
    private static final String DEFAULT_RISK_CATEGORY = "NORMAL";

    private final JpaRiskReportRepository riskReportRepository;

    public RiskReportAdapter(JpaRiskReportRepository riskReportRepository) {
        this.riskReportRepository = riskReportRepository;
    }

    @Override
    public RiskReport requestRiskReport(UUID customerId, BigDecimal requestedAmount) {
        log.info("Solicitando informe de riesgo para cliente {} con monto {}", customerId, requestedAmount);
        
        int riskScore = calculateRiskScore(customerId, requestedAmount);
        String riskCategory = categorizeRisk(riskScore);
        boolean isEligible = riskScore >= 600 && requestedAmount.compareTo(getMaxAllowedAmount(customerId)) <= 0;
        
        RiskReportEntity entity = new RiskReportEntity();
        entity.setId(UUID.randomUUID());
        entity.setCustomerId(customerId);
        entity.setBureauName(BUREAU_NAME);
        entity.setRiskScore(riskScore);
        entity.setRiskCategory(riskCategory);
        entity.setEligible(isEligible);
        entity.setRequestedAmount(requestedAmount);
        entity.setMaxAllowedAmount(getMaxAllowedAmount(customerId));
        entity.setReportDate(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
        
        RiskReportEntity saved = riskReportRepository.save(entity);
        log.info("Informe de riesgo generado con score {} y categoría {} para cliente {}", 
                riskScore, riskCategory, customerId);
        
        return toDomain(saved);
    }

    @Override
    public Optional<RiskReport> getRiskReportById(UUID reportId) {
        log.debug("Consultando informe de riesgo con ID {}", reportId);
        return riskReportRepository.findById(reportId).map(this::toDomain);
    }

    @Override
    public Optional<RiskReport> getLatestRiskReport(UUID customerId) {
        log.debug("Consultando último informe de riesgo para cliente {}", customerId);
        return riskReportRepository.findTopByCustomerIdOrderByReportDateDesc(customerId)
                .map(this::toDomain);
    }

    private int calculateRiskScore(UUID customerId, BigDecimal requestedAmount) {
        int baseScore = DEFAULT_RISK_SCORE;
        
        if (requestedAmount.compareTo(BigDecimal.valueOf(50000)) > 0) {
            baseScore -= 100;
        } else if (requestedAmount.compareTo(BigDecimal.valueOf(10000)) > 0) {
            baseScore -= 50;
        }
        
        if (requestedAmount.compareTo(BigDecimal.valueOf(100000)) > 0) {
            baseScore -= 150;
        }
        
        return Math.max(300, Math.min(850, baseScore));
    }

    private String categorizeRisk(int riskScore) {
        if (riskScore >= 750) {
            return "EXCELLENT";
        } else if (riskScore >= 700) {
            return "GOOD";
        } else if (riskScore >= 650) {
            return "FAIR";
        } else if (riskScore >= 600) {
            return "ACCEPTABLE";
        } else {
            return DEFAULT_RISK_CATEGORY;
        }
    }

    private BigDecimal getMaxAllowedAmount(UUID customerId) {
        return BigDecimal.valueOf(50000);
    }

    private RiskReport toDomain(RiskReportEntity entity) {
        return new RiskReport(
                entity.getId(),
                entity.getCustomerId(),
                entity.getBureauName(),
                entity.getRiskScore(),
                entity.getRiskCategory(),
                entity.isEligible(),
                entity.getRequestedAmount(),
                entity.getMaxAllowedAmount(),
                entity.getReportDate()
        );
    }
}

// === ARCHIVO: src/main/java/com/pragma/loans/infrastructure/config/DatabaseConfig.java ===
package com.pragma.loans.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    
    private static final int MAX_CONNECTIONS = 200;
    private static final int MIN_IDLE_CONNECTIONS = 20;
    private static final long CONNECTION_TIMEOUT_MS = 10000L;
    private static final long IDLE_TIMEOUT_MS = 300000L;
    private static final long MAX_LIFETIME_MS = 600000L;
    private static final int CACHE_PREPARE_STATEMENTS = 250;
    private static final int PREPARE_STATEMENT_CACHE_SIZE = 2048;

    @Value("${spring.datasource.url:jdbc:postgresql://localhost:5432/loans}")
    private String jdbcUrl;

    @Value("${spring.datasource.username:postgres}")
    private String username;

    @Value("${spring.datasource.password:postgres}")
    private String password;

    @Value("${spring.datasource.driver-class-name:org.postgresql.Driver}")
    private String driverClassName;

    @Value("${spring.jpa.properties.hibernate.dialect:org.hibernate.dialect.PostgreSQLDialect}")
    private String hibernateDialect;

    @Value("${spring.jpa.show-sql:false}")
    private boolean showSql;

    @Bean
    @Primary
    public DataSource dataSource() {
        log.info("Configurando HikariCP con pool de {} conexiones máximo para soportar 1500 req/s", 
                MAX_CONNECTIONS);
        
        HikariConfig config = new HikariConfig();
        config.setPoolName("LoansHikariPool");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        
        config.setMaximumPoolSize(MAX_CONNECTIONS);
        config.setMinimumIdle(MIN_IDLE_CONNECTIONS);
        config.setConnectionTimeout(CONNECTION_TIMEOUT_MS);
        config.setIdleTimeout(IDLE_TIMEOUT_MS);
        config.setMaxLifetime(MAX_LIFETIME_MS);
        config.setConnectionTestQuery("SELECT 1");
        config.setAutoCommit(true);
        
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", CACHE_PREPARE_STATEMENTS);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", PREPARE_STATEMENT_CACHE_SIZE);
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        
        config.setRegisterMbeans(true);
        config.setAllowPoolSuspension(false);
        config.setReadOnly(false);
        config.setIsolateInternalQueries(false);
        
        HikariDataSource dataSource = new HikariDataSource(config);
        
        log.info("HikariCP inicializado: pool={}, max={}, minIdle={}, timeout={}ms",
                config.getPoolName(), config.getMaximumPoolSize(), 
                config.getMinimumIdle(), config.getConnectionTimeout());
        
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        log.info("Configurando EntityManagerFactory con Hibernate para optimización de rendimiento");
        
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.pragma.loans.infrastructure.entities");
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(showSql);
        em.setJpaVendorAdapter(vendorAdapter);
        
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "50");
        hibernateProperties.setProperty("hibernate.order_inserts", "true");
        hibernateProperties.setProperty("hibernate.order_updates", "true");
        hibernateProperties.setProperty("hibernate.jdbc.batch_versioned_data", "true");
        hibernateProperties.setProperty("hibernate.generate_statistics", "false");
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", "false");
        hibernateProperties.setProperty("hibernate.default_batch_fetch_size", "16");
        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", "50");
        hibernateProperties.setProperty("hibernate.connection.release_mode", "AFTER_TRANSACTION");
        hibernateProperties.setProperty("hibernate.transaction.coordinator_class", "jdbc");
        
        em.setJpaProperties(hibernateProperties);
        
        log.info("EntityManagerFactory configurado con batch_size=50, fetch_size=50");
        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        log.info("Configurando JpaTransactionManager");
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        transactionManager.setDataSource(dataSource());
        transactionManager.setDefaultTimeout(30);
        return transactionManager;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        log.info("Configurando JdbcTemplate con DataSource optimizado");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setQueryTimeout(10);
        jdbcTemplate.setFetchSize(50);
        jdbcTemplate.setMaxRows(10000);
        return jdbcTemplate;
    }

    @Bean
    public ScheduledExecutorService healthCheckExecutor() {
        log.info("Configurando ScheduledExecutorService para monitoreo de salud del pool");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, r -> {
            Thread t = new Thread(r, "HikariPool-HealthCheck");
            t.setDaemon(true);
            return t;
        });
        return executor;
    }

    @PostConstruct
    public void logPoolConfiguration() {
        log.info("========================================");
        log.info("CONFIGURACIÓN DE BASE DE DATOS");
        log.info("========================================");
        log.info("URL: {}", jdbcUrl);
        log.info("Driver: {}", driverClassName);
        log.info("Pool máximo: {} conexiones", MAX_CONNECTIONS);
        log.info("Pool mínimo: {} conexiones", MIN_IDLE_CONNECTIONS);
        log.info("Connection timeout: {}ms", CONNECTION_TIMEOUT_MS);
        log.info("Idle timeout: {}ms", IDLE_TIMEOUT_MS);
        log.info("Max lifetime: {}ms", MAX_LIFETIME_MS);
        log.info("Statement cache: {} statements", PREPARE_STATEMENT_CACHE_SIZE);
        log.info("========================================");
    }
}

// === ARCHIVO: src/test/java/com/pragma/loans/application/usecase/LoanUseCaseTest.java ===
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

// === ARCHIVO: src/test/java/com/pragma/loans/infrastructure/adapters/LoanJpaAdapterTest.java ===
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

// === ARCHIVO: docs/modelo_actual.md ===
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

// === ARCHIVO: docs/modelo_normalizado.md ===
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

// === ARCHIVO: docs/validacion.md ===
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
```
