# Spring Boot Specmatic with Customer CRUD API

[![CI](https://github.com/hendisantika/spring-boot-specmatis-sample/actions/workflows/ci.yml/badge.svg)](https://github.com/hendisantika/spring-boot-specmatis-sample/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.16-6DB33F?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.9.16-C71A36?logo=apachemaven)
![MySQL](https://img.shields.io/badge/MySQL-9.3.0-4479A1?logo=mysql&logoColor=white)
[![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)](.github/dependabot.yml)

This project demonstrates a Spring Boot application with:

1. A simple Hello API
2. A complete CRUD REST API for Customer management
3. MySQL database with Docker Compose
4. Specmatic for API contract testing
5. Swagger UI for API documentation
6. GitHub Actions CI on JDK 25 and automated dependency updates with Dependabot

## 🧰 Tech Stack

| Component          | Version                 |
|--------------------|-------------------------|
| Java (JDK)         | 25 (Eclipse Temurin)    |
| Spring Boot        | 3.5.16                  |
| springdoc-openapi  | 2.8.17                  |
| Maven (wrapper)    | 3.9.16                  |
| MySQL              | 9.3.0                   |
| Specmatic          | 2.15.0                  |

## ⚙️ Prerequisites

- JDK 25 (e.g. `sdk install java 25-tem`)
- Docker & Docker Compose (for MySQL)
- No local Maven needed; use the bundled `./mvnw` wrapper

## 📦 Project Structure

```shell
spring-boot-specmatic/
├── .github/
│ ├── dependabot.yml
│ └── workflows/
│     └── ci.yml
├── src/main/java/id/my/hendisantika/specmatic/
│ ├── HelloController.java
│ ├── SpringBootSpecmaticApplication.java
│ ├── config/
│ │   └── OpenApiConfig.java
│ └── customer/
│     ├── Customer.java
│     ├── CustomerRepository.java
│     ├── CustomerService.java
│     ├── CustomerController.java
│     ├── CustomerNotFoundException.java
│     ├── CustomerAlreadyExistsException.java
│     └── DataLoader.java
├── src/main/resources/
│ └── application.properties
├── pom.xml
├── api.yaml
├── Dockerfile
├── compose.yml
├── compose2.yml
└── specmatic.jar
```

## ✅ 1. Spring Boot REST API
HelloController.java

```java
package id.my.hendisantika.specmatic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Hello", description = "Hello World API")
public class HelloController {

    @Operation(summary = "Get a hello message", description = "Returns a simple hello world message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the hello message",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello, World!");
    }
}
```

## ✅ 2. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.16</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>id.my.hendisantika</groupId>
    <artifactId>pecmatic</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-boot-specmatic</name>
    <description>spring-boot-specmatic</description>
    <properties>
        <java.version>25</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-docker-compose</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- SpringDoc OpenAPI UI -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.8.17</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

## ✅ 3. OpenAPI Contract: api.yaml

```yaml
openapi: 3.0.0
info:
  title: Hello API
  version: 1.0.0
paths:
  /hello:
    get:
      responses:
        '200':
          description: Say Hello
          content:
            application/json:
              schema:
                type: object
                properties:
                  message:
                    type: string
                    example: Hello, World!

```

## ✅ 4. Download Specmatic Jar

```shell
wget https://github.com/specmatic/specmatic/releases/download/2.15.0/specmatic.jar
```

## 🚀 5. Run the App & Proxy
5.1 Start Spring Boot App

```shell
./mvnw spring-boot:run
```

The app runs at http://localhost:8080

5.2 Start Specmatic Proxy (Command Line)

```shell
java -jar specmatic.jar proxy --host localhost --port 9000 --target http://localhost:8080 ./testing
```

## ✅ 6. Test Through Proxy

```shell
curl http://localhost:9000/hello
```

You'll get the proxied response if valid. If it doesn't match the contract, Specmatic will print validation errors.

## 🔄 Customer CRUD API

The application includes a complete CRUD API for Customer management with the following endpoints:

### Endpoints

| Method | URL                 | Description                 | Status Codes                           |
|--------|---------------------|-----------------------------|----------------------------------------|
| GET    | /api/customers      | Get all customers           | 200 OK                                 |
| GET    | /api/customers/{id} | Get customer by ID          | 200 OK, 404 Not Found                  |
| POST   | /api/customers      | Create a new customer       | 201 Created, 400 Bad Request           |
| PUT    | /api/customers/{id} | Update an existing customer | 200 OK, 400 Bad Request, 404 Not Found |
| DELETE | /api/customers/{id} | Delete a customer           | 204 No Content, 404 Not Found          |

### Customer Model

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890"
}
```

### Sample Requests

#### Get all customers

```shell
curl -X GET http://localhost:8080/api/customers
```

#### Get customer by ID

```shell
curl -X GET http://localhost:8080/api/customers/1
```

#### Create a new customer

```shell
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"name":"New Customer","email":"new.customer@example.com","phone":"9876543210"}'
```

#### Update a customer

```shell
curl -X PUT http://localhost:8080/api/customers/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated Name","email":"john.doe@example.com","phone":"1234567890"}'
```

#### Delete a customer

```shell
curl -X DELETE http://localhost:8080/api/customers/1
```

## 🐳 Docker Support

The application includes Docker support for easy deployment and development. Docker allows you to run the application
and its dependencies in isolated containers.

### Dockerfile

The project includes a Dockerfile that builds the application and creates a Docker image:

```dockerfile
# Dockerfile
FROM eclipse-temurin:25-jdk-noble AS build
LABEL authors="hendisantika"

WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:25-jre-noble
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose

The project includes Docker Compose files for orchestrating the application and its dependencies:

```yaml
# compose.yml
services:
  mysql:
    image: mysql:9.3.0
    container_name: mysql
    environment:
      - MYSQL_ROOT_PASSWORD=53cret
      - MYSQL_DATABASE=customerdb
      - MYSQL_USER=yu71
      - MYSQL_PASSWORD=53cret
    ports:
      - "3309:3306"
    volumes:
      - ./mysql-data:/var/lib/mysql

volumes:
  mysql-data:
```

### Running with Docker

To build and run the application with Docker:

```shell
# Build the Docker image
docker build -t spring-boot-specmatic .

# Run MySQL only
docker compose up -d

# Run app + MySQL + Specmatic proxy (JDK 25)
docker compose -f compose2.yml up --build
```

## 📝 API Documentation with Swagger UI

The application includes Swagger UI for interactive API documentation. Swagger UI provides a user-friendly interface to
explore and test the API endpoints.

### Accessing Swagger UI

You can access the Swagger UI at:

```
http://localhost:8080/swagger-ui
```

### Features

- Interactive API documentation
- Test API endpoints directly from the browser
- View request and response models
- Explore available endpoints and their parameters
- Download OpenAPI specification

### Integration with OpenAPI Specification

The Swagger UI is integrated with the existing OpenAPI specification (api.yaml) and enhanced with additional metadata
through the OpenApiConfig class.

## 💾 MySQL Database

The application uses MySQL for storing customer data.

### Database Configuration

The database configuration is defined in `application.properties`:

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://mysql:3306/customerdb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=yu71
spring.datasource.password=53cret
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Docker Integration

The MySQL database is configured to run in a Docker container using Docker Compose. The database configuration in
`compose.yml` includes:

- MySQL version: 9.3.0
- Database name: customerdb
- Username: yu71
- Password: 53cret
- Port mapping: 3309:3306
- Persistent volume: mysql-data

### Sample Data

The application automatically loads sample customer data on startup using the `DataLoader` class.

## 🧪 Build & Test Locally

The context-load test needs a running MySQL. Start it with Docker and point the datasource at it:

```shell
docker compose up -d
SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3309/customerdb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true' \
  ./mvnw verify
```

## 👷 Continuous Integration

GitHub Actions ([`.github/workflows/ci.yml`](.github/workflows/ci.yml)) runs on every push and pull request to `main`:

1. **Build & Test (JDK 25)**: sets up Temurin JDK 25 with Maven caching, starts a MySQL 9.3.0 service container and
   runs `./mvnw verify`. Surefire reports are uploaded as an artifact when the build fails.
2. **Docker image build**: builds the Docker image with Buildx (GitHub Actions cache enabled) to make sure the
   `Dockerfile` keeps working.

## 🤖 Dependabot

Dependabot ([`.github/dependabot.yml`](.github/dependabot.yml)) checks for updates weekly (Monday 06:00, Asia/Jakarta):

| Ecosystem        | What it updates                   | Grouping / rules                                                 |
|------------------|-----------------------------------|------------------------------------------------------------------|
| `maven`          | `pom.xml` dependencies & parent   | Spring Boot, springdoc, and other minor/patch updates are grouped |
| `github-actions` | Actions used in workflows         | All bumps grouped into a single PR                               |
| `docker`         | `Dockerfile` base images          | `eclipse-temurin` stays on the JDK 25 line (major bumps ignored) |
| `docker-compose` | `compose.yml` images              | n/a                                                              |

Pull requests are labeled (`dependencies` plus the ecosystem) and use conventional commit prefixes such as
`build(deps):` and `ci(deps):`.
