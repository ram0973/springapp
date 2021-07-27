FROM adoptopenjdk/openjdk16:jdk-16.0.1_9-debian-slim
LABEL maintainer="ram0973@gmail.com"
RUN adduser --system --group spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080