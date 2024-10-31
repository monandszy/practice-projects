# FoodDelivery

**Written:** 2024.02.29 - 2024.03.09

# Project Overview

This was my first attempt at developing a larger-scale Spring web application. 
In retrospect, several architectural mistakes were made, including overly granular packaging, a misunderstanding of the domain concept, and inadequate separation of entity-table relationships. 
These issues made it difficult to develop new features and conduct testing. 
However, this served as a valuable architectural learning experience.

## Key Features
- **Testing:** Tests are divided into two main categories:
  - **Context-Split Service Tests:** Utilizing `@DataJpaTest` with Testcontainers.
  - **Web Tests:** Use `@WebMvcTest` to focus on controller testing.
- **REST API:** The project includes a small REST API with Swagger UI documentation. It also integrates an external API using OpenAPI autogeneration.
- **Thymeleaf & Security:** The Thymeleaf template engine is used alongside Spring MVC and Spring Security to implement a simple html ui and role-based features.

#### How to use:
- Application starts on port localhost:8087/code
- To get a role and use features, register a new account, login as admin (password provided below) and assign a role to created account

### Features as Admin:

- CRUD Accounts - assign roles
- default admin account: Username: "admin" Password: "admin"

### Features as Seller:

- CRUD Restaurants - Address is assigned based on your ip
- CRUD Menus
- CRUD MenuPositions - Add a picture to each one
- See Complete and Incomplete Orders - Complete Orders

### Features as Client:

- Find restaurants nearest to you based on ip and restaurant's available delivery range
- Read Menus
- Read MenuPositions - Create Order
- Read Your Orders and Their Status - Cancel Order within 20 minutes

### Rest API Features:

- Create a full restaurant in one command
- CRUD Orders (as a Client)

#### Component Diagram

<img src="/diagrams/component.svg" alt="image" height="500">

#### ERD Diagram

<img src="/diagrams/erd.png" alt="image" height="500">

#### Jacoco Test Coverage

<img src="/diagrams/jacoco.png" alt="image">