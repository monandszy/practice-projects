# Mortgage Calculator

This module is a Java practice project that implements mortgage rate calculation logic, supports different rate types (constant and decreasing), and models overpayments (reduce rate / reduce duration). It includes formatting/printing utilities, serialization helpers, and unit tests that validate financial calculations.

## What this project does
- Calculates mortgage repayment schedules and summaries based on input parameters.
- Supports both constant and decreasing repayment schemes.
- Handles overpayments with two strategies: reduce rate or reduce duration.
- Produces formatted tables of rates and summary output; supports multiple output targets (console, file, log).

## Key packages & classes
- `code.MortgageCalculator` — main entry point. It demonstrates two ways of bootstrapping the calculation: manual wiring and Spring-based bean creation (`ApplicationConfiguration`).
- `code.ApplicationConfiguration` — Spring configuration that component-scans the service package and wires default `InputService` and `OutputService` beans.
- `code.PlantUmlCreator` — helper to generate PlantUML diagrams (project includes a `plantUML/` folder).
- `code.model` — domain model classes:
  - `InputData` — all input parameters and scales/formatting settings.
  - `OverpaymentData`, `OverpaymentType` — overpayment model.
  - `Reference`, `Summary`, `FormattingValues` — supporting domain objects.
  - `model.rate` — rate-related domain objects (`Rate`, `RateAmounts`, `MortgageResidual`, `TimePoint`, `RateType`).
- `code.service` — core services and orchestration:
  - `MortgageCalculationService` / `MortgageCalculationServiceI` — high-level calculation orchestration.
  - `SummaryCalculationService` / `SummaryCalculationServiceI` — produce overall summary values.
  - `service.rateCalculation` — detailed calculation components:
    - `RateCalculationService` — generates rates over time.
    - `TimePointService`, `AmountsCalculationService`, `ResidualCalculationService` — smaller calculation responsibilities.
  - `service.printing` — formatting and printing services for table output:
    - `TableFormattingService`, `ColumnFormattingService`, `RowFormattingService`, `SizeFormattingService` and `PrintingService`.
  - `service.input` — `InputServiceFactory` with `InputFromBuilder` and `InputFromFile` implementations (choose interactive/builder or file-based inputs).
  - `service.serialization` — serialization helpers used to persist or export results.

## Tests
- Tests are JUnit 5 based and located under `src/test/java`.
- Notable test: `code.service.RateValuesCalculationTest` — verifies rate amounts, mortgage residuals, overpayment scenarios, and time point calculations using deterministic fixtures under `src/test/java/code/fixtures`.