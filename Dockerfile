# Build Stage
FROM eclipse-temurin:21-jdk AS build
WORKDIR /opt/app
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw --batch-mode dependency:go-offline
COPY src src
RUN ./mvnw package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21-jre
WORKDIR /opt/app
COPY --from=build /opt/app/target/quoteweaver-*-runner.jar quoteweaver-runner.jar
EXPOSE 3003
CMD ["java", "-jar", "/opt/app/quoteweaver-runner.jar"]
