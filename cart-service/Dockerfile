FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/cart-service-0.0.1-SNAPSHOT.jar /app/cart-service.jar
CMD ["java", "-jar", "cart-service.jar"]
EXPOSE 8084