# Microservices Project

This project is a microservices-based application built with Java, Spring Boot, and Maven. It includes several services such as user-service, product-service, cart-service, order-service, payment-service, and a gateway-service. The services are registered with Eureka Server and communicate with each other.

## Services

- **Gateway Service**: Acts as an entry point for the application.
- **Eureka Server**: Service registry for registering and discovering services.
- **User Service**: Manages user-related operations.
- **Product Service**: Manages product-related operations.
- **Cart Service**: Manages shopping cart operations.
- **Order Service**: Manages order-related operations.
- **Payment Service**: Manages payment-related operations.
- **Kafka**: Message broker for asynchronous communication.
- **Zookeeper**: Coordinates distributed systems.

## Prerequisites

- Java 17
- Maven
- Docker
- Docker Compose

## Building and Running

1. **Build the project**:
    ```sh
    mvn clean install
    ```

2. **Start the services using Docker Compose**:
    ```sh
    docker-compose up --build
    ```

3. **Access the services**:
    - Gateway Service: `http://localhost:8080`
    - Eureka Server: `http://localhost:8761`
    - User Service: `http://localhost:8083`
    - Product Service: `http://localhost:8081`
    - Cart Service: `http://localhost:8084`
    - Order Service: `http://localhost:8082`
    - Payment Service: `http://localhost:8085`
    - Kafka: `http://localhost:9092`
    - Zookeeper: `http://localhost:2181`

## Dockerfiles

Each service has its own `Dockerfile` for building Docker images. For example, the `Dockerfile` for the `order-service` is:

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/order-service-0.0.1-SNAPSHOT.jar /app/order-service.jar
CMD ["java", "-jar", "order-service.jar"]
EXPOSE 8082