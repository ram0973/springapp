FROM openjdk:21 AS base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src

FROM base as test
CMD ["./mvnw", "test"]

FROM base as development
CMD ["./mvnw", "spring-boot:run"]

FROM base as build
RUN ./mvnw package

FROM base AS production
COPY --from=build /app/target/*.jar /spring-app.jar
RUN adduser --system springapp
USER springapp
CMD ["java", "-jar", "/spring-app.jar"]
EXPOSE 8080