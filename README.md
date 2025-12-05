# Modules Overview

This repository contains multiple of my Java practice projects.

## Modules

- entity-cycle: Project focused on using Hibernate (without Spring JPA) and Flyway migrations. Uses a layered architecture with MapStruct mappers and a DAO layer. Includes a simulation `CycleService` that models `Creature` entities, food assignment and lifecycle rules. Good example of low-level Hibernate usage and Flyway-managed DB migrations.

- online-store: Practice project demonstrating Spring JDBC in a layered architecture. Implements a generic `CRUDRepository<T>` abstraction to reduce boilerplate and experiments with mapping/query strategies. Contains relations like `Customer`, `Producer`, `Product`, `Purchase`, `Opinion`. Useful for JDBC-based approaches and mapping strategies without full JPA.

- food-delivery-project: Larger Spring web application with REST API, Thymeleaf views, Spring Security, Swagger, Testcontainers-based tests, and Docker support. Contains a small REST API, role-based features, and instructions about running on `localhost:8087/code`. Default admin credentials: `admin` / `admin`.

- mortgage-calculator: Java 17 practice project that computes mortgage schedules (constant and decreasing rates), supports overpayments (reduce rate/duration), and includes JUnit 5 tests validating rate/residual logic.

- todo-list: Command-driven ToDo CLI that executes semicolon-separated commands against PostgreSQL using plain JDBC. Includes a simple command builder, query factory and a `DatabaseRunnerImpl` for executing parameterized queries.


