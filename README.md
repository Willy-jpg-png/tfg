# RepEat REST API

This project contains the backend of **RepEat**, a real-time food delivery web application. It is developed using Java 21 and Spring Boot, and follows a hexagonal architecture.

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Spring WebSocket
- OpenAPI / Swagger
- MySQL
- Maven

## SetUp

### 1. Prerequisites

- [Java 21 JDK](https://www.oracle.com/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/mysql/)
- (Optional) [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)

### 2. Clone the repository

```bash
git clone https://github.com/your-username/rep-eat-backend.git
cd rep-eat-backend
```

### 3. Configure the database
Make sure to create a MySQL schema (e.g. repeat) and update the connection info in src/main/resources/application.yml:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/repeat
    username: your_mysql_user
    password: your_mysql_password
  jpa:
    hibernate:
      ddl-auto: update
  flyway:
    enabled: true
```
You can manage schema versions and changes using Flyway and optionally MySQL Workbench for visual modeling and migrations.

### 4. Build the project

```bash
mvn clean install
```

### 5. Run the application
```bash
mvn spring-boot:run
```

By default, the API will be available at: http://localhost:8080/tfg/api

