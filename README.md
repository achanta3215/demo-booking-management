# Glofox Booking Management App

## Setup
- Install dependencies and Run the application on Port 8080 in embedded Tomcat sever
```shell
./mvnw spring-boot:run
```

## Tech Stack

- **Java 21**, **Spring Boot 3.3.4**
- **H2 Database**: In memory database
- **Lombok**: To reduce the boilerplate code (who likes writing getters and setters anyway?).
- **OpenAPI**: [API documentation](http://localhost:8080/swagger-ui/index.html)

### Guid to Interact with API
* Install [HTTPie CLI](https://httpie.io/cli) client to perform REST operations
* As a Studio owner create a new class
```shell
 http POST http://localhost:8080/classes className="Yoga Class" startDate="2024-10-01T00:00:00" endDate="2024-10-20T00:00:00" capacity:=30
```

* To fetch all paginated list of classes created
```shell
http localhost:8080/classes
```