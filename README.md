# Greeting Service

A simple greeting service built with Spring Boot following DDD and Hexagonal architecture principles.

## Structure

```
greeting-service/
├── api/              # Protobuf event definitions
├── domain/           # Business logic, aggregates, repository interfaces
├── data/             # JDBC repositories, Flyway migrations
├── client/           # REST client interfaces (published library)
├── server/           # Spring Boot application, REST controllers
└── settings.gradle.kts
```

## Modules

- **api**: Protobuf event definitions (`GreetingCreatedEvent`, `GreetingMessage`)
- **domain**: Core business logic with `Greeting` aggregate and `GreetingRepository` interface
- **data**: JDBC implementation of repository with Flyway migrations
- **client**: REST client interfaces for consuming the service (published as Maven artifact)
- **server**: Spring Boot web application with REST API

## Running the Service

### Prerequisites

- PostgreSQL database
- Java 21
- Gradle 9.1+

### Local Development

1. Start PostgreSQL:
   ```bash
   docker run -d --name postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=greeting_db -p 5432:5432 postgres:15
   ```

2. Run the service:
   ```bash
   ./gradlew :server:bootRun
   ```

3. The service will be available at `http://localhost:8080`

## API Endpoints

### Create Greeting
```bash
POST /api/v1/greetings
Content-Type: application/json

{
  "message": "Hello, World!",
  "recipient": "John"
}
```

### Get Greeting
```bash
GET /api/v1/greetings/{id}
```

### Delete Greeting
```bash
DELETE /api/v1/greetings/{id}
```

## Building

```bash
# Build all modules
./gradlew build

# Run tests
./gradlew check
```

## Architecture

This service follows:
- **DDD**: Domain-driven design with aggregates
- **Hexagonal Architecture**: Domain separated from infrastructure
- **Immutable Aggregates**: Records with validation
- **JDBC**: Direct JDBC with JdbcClient (no JPA)
