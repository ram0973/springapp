FROM adoptopenjdk/openjdk16:jdk-16.0.1_9-debian-slim AS build
LABEL maintainer="ram0973@gmail.com"
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package

FROM adoptopenjdk/openjdk16:jre-16.0.1_9-debianslim as production
COPY --from=build /app/target/*.jar /spring-app.jar
RUN adduser --system springapp
USER springapp
CMD ["java", "-jar", "/spring-app.jar"]
EXPOSE 8080