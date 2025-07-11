Specmatic: Things Todo List

Here's a step-by-step guide to run Specmatic in proxy mode and a working example.

📦 Project Structure

```shell
spring-boot-specmatic/
├── src/main/java/com/example/demo/
│ └── HelloController.java
├── pom.xml
├── api.yaml
└── specmatic.jar
```

✅ 1. Spring Boot REST API
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

✅ 2. pom.xml (Minimal)

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

✅ 3. OpenAPI Contract: api.yaml

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

✅ 4. Download Specmatic Jar

```shell
wget https://github.com/specmatic/specmatic/releases/download/2.15.0/specmatic.jar
```

🚀 5. Run the App & Proxy
5.1 Start Spring Boot App

```shell
mvn spring-boot:run
```

# runs at http://localhost:8080

5.2 Start Specmatic Proxy (Command Line)

```shell
java -jar specmatic.jar proxy --host localhost --port 9000 --target http://localhost:8080 ./testing
```

✅ 6. Test Through Proxy

```shell
curl http://localhost:9000/hello
```

You’ll get the proxied response if valid. If it doesn't match the contract, Specmatic will print validation errors.



