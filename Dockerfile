# Étape 1 : Compilation de l'application Spring Boot
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

# Étape 2 : Exécution de l'application Spring Boot
FROM adoptopenjdk:17-jre-hotspot
WORKDIR /app
COPY --from=build /app/target/feteKorner-0.0.1-SNAPSHOT.jar /app/feteKorner.jar
EXPOSE 8080
CMD ["java", "-jar", "feteKorner.jar"]