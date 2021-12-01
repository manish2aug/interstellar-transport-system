# Interstellar Transport System

A system to compute the shortest path from source planet to destination

## Prerequisites

- JDK 1.8
- Maven 3+

## Tech & Spec
- Language: Java 1.8
- Runtime: Tomcat
- Spring 5
- H2 in-memory database
- Apache Poi
- Thymeleaf

## Usage

### Dev mode
```bash
git clone git@github.com:manish2aug/interstellar-transport-system.git
cd interstellar-transport-system
mvn spring-boot:run
```
### Uber jar
```bash
git clone git@github.com:manish2aug/interstellar-transport-system.git
cd interstellar-transport-system
mvn clean install
java -jar target/interstellar-transport-system-1.0.jar 
```

#### Access application at http://localhost:8080/interstellar

## Documentation
- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [Soap (WSDL)](http://localhost:8080/ws/routes.wsdl)

