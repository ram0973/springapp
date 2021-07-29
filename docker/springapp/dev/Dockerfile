FROM adoptopenjdk/openjdk16:jdk-16.0.1_9-debian-slim
LABEL maintainer="ram0973@gmail.com"
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]
EXPOSE 8080