FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copy the jar file into the container
COPY subscription-service-0.0.1-SNAPSHOT.jar subscription-service-0.0.1-SNAPSHOT.jar
# Make the port available to the outside world
EXPOSE 8080
# Run the jar file
CMD ["java", "-jar", "subscription-service-0.0.1-SNAPSHOT.jar"]
