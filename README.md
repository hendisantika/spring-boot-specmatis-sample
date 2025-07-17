# Spring Boot Specmatic with Customer CRUD API

This project demonstrates a Spring Boot application with:

1. A simple Hello API
2. A complete CRUD REST API for Customer management
3. H2 in-memory database
4. Specmatic for API contract testing

## 📦 Project Structure

```shell
spring-boot-specmatic/
├── src/main/java/id/my/hendisantika/specmatic/
│ ├── HelloController.java
│ ├── SpringBootSpecmaticApplication.java
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
└── specmatic.jar
```

## ✅ 1. Spring Boot REST API
HelloController.java

```java
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello, World!");
    }

}
```

## ✅ 2. pom.xml (Minimal)

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>spring-boot-specmatic</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.3</version>
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
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
mvn spring-boot:run
```

# runs at http://localhost:8080

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

## 💾 H2 Database

The application uses an H2 in-memory database for storing customer data.

### H2 Console

The H2 console is enabled and can be accessed at:

```
http://localhost:8080/h2-console
```

Connection details:

- JDBC URL: `jdbc:h2:mem:customerdb`
- Username: `sa`
- Password: `password`

### Sample Data

The application automatically loads sample customer data on startup using the `DataLoader` class.