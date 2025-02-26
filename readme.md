# Spring Boot Starter Template

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

A comprehensive Spring Boot starter template for building robust, production-ready applications. This template includes essential dependencies and configurations for security, database access, monitoring, caching, and more.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [Docker Setup](#docker-setup)
- [API Documentation](#api-documentation)
- [Database Configuration](#database-configuration)
- [Security](#security)
- [Monitoring and Observability](#monitoring-and-observability)
- [Internationalization](#internationalization)
- [Contributing](#contributing)
- [License](#license)

## Features

- ✅ **Clean Architecture** - Separation of concerns
- ✅ **Comprehensive Security** - JWT authentication, role-based access control
- ✅ **Database Integration** - JPA/Hibernate with PostgreSQL
- ✅ **API Documentation** - Automated OpenAPI documentation
- ✅ **Caching** - Redis for high-performance caching
- ✅ **Messaging** - WebSocket support for real-time communication
- ✅ **Monitoring** - Prometheus and Grafana integration
- ✅ **Logging** - Centralized logging with ELK stack support
- ✅ **Notifications** - Firebase integration for push notifications
- ✅ **Internationalization** - Multi-language support
- ✅ **Exception Handling** - Global exception handling with meaningful responses
- ✅ **Email Templates** - Ready-to-use Thymeleaf and FreeMarker templates
- ✅ **Circuit Breaker** - Resilience4j for fault tolerance
- ✅ **Docker Support** - Containerization with Docker Compose

## Technologies

- **Java 21**
- **Spring Boot 3.3.5**
- **Spring Security** with JWT Authentication
- **Spring Data JPA** with PostgreSQL
- **Spring WebSocket**
- **Redis** for caching
- **Firebase Admin** for push notifications
- **Lombok** for reducing boilerplate code
- **MapStruct** for object mapping
- **OpenAPI/Swagger** for API documentation
- **Prometheus & Grafana** for monitoring
- **ELK Stack** for centralized logging
- **Docker** for containerization

## Project Structure

The project follows a domain-driven design with a clear separation of concerns:

```
src/main/java/com/gucardev/springbootstartertemplate/
├── domain                      # Domain-specific code
│   ├── common                  # Shared domain components
│   │   └── entity              # Base entities
│   └── user                    # User domain
│       ├── controller          # REST controllers
│       ├── entity              # Domain entities
│       ├── enumeration         # Enums
│       ├── event               # Domain events
│       ├── mapper              # Object mappers
│       ├── model               # DTOs and request models
│       ├── repository          # Data access
│       ├── scheduler           # Scheduled tasks
│       └── usecase             # Business logic
└── infrastructure              # Technical infrastructure
    ├── annotation              # Custom annotations
    ├── config                  # Application configurations
    ├── constants               # Constants
    ├── exception               # Exception handling
    ├── filter                  # Request filters
    ├── response                # Response models
    ├── usecase                 # Use case interfaces
    └── util                    # Utility classes
```

## Getting Started

### Prerequisites

- JDK 21 or later
- Maven 3.6+ or Gradle 7.0+
- Docker and Docker Compose (for running infrastructure components)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/spring-boot-starter-template.git
   cd spring-boot-starter-template
   ```

2. Configure application properties:
    - Update `src/main/resources/application.properties` with your configuration
    - Set up database credentials and other environment-specific settings

3. Build the project:
   ```bash
   mvn clean install
   ```

### Running the Application

#### Development Mode

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Production Mode

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

#### Testing

```bash
mvn test
```

## Docker Setup

The template includes a comprehensive Docker Compose setup for development:

1. Start all services:
   ```bash
   docker-compose up -d
   ```

2. Available services:
    - PostgreSQL database
    - Redis cache
    - Prometheus for metrics
    - Grafana for dashboards
    - ELK Stack for logging

## API Documentation

Once the application is running, the OpenAPI documentation is available at:
```
http://localhost:8080/swagger-ui.html
```

## Database Configuration

The template uses PostgreSQL by default, but you can easily switch to another database:

1. Update the database dependency in `pom.xml`
2. Modify the database configuration in `application.properties`

## Security

The application includes a comprehensive security setup:

- JWT-based authentication
- Role-based authorization
- Password encryption
- CORS configuration
- Security headers

## Monitoring and Observability

- **Prometheus**: Collects metrics exposed by Spring Boot Actuator
- **Grafana**: Visualizes metrics with pre-configured dashboards
- **Logback**: Structured logging with JSON format support
- **ELK Stack**: For log aggregation and analysis

## Internationalization

The template supports multiple languages:
- Default messages in `messages.properties`
- Turkish translations in `messages_tr.properties`
- Add more languages by creating additional properties files

## License

This project is licensed under the MIT License - see the LICENSE file for details.