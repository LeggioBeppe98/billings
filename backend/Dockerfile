# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia pom.xml e scarica le dipendenze (cache)
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copia il codice e builda
COPY src ./src
RUN mvn -q package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia il jar dal build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]