FROM openjdk:17
WORKDIR /app
COPY target/payment-service-0.0.1-SNAPSHOT.jar /app/payment-service.jar
CMD ["java", "-jar", "payment-service.jar"]
EXPOSE 8085