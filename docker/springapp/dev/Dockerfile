FROM maven:3.8.1-openjdk-16
LABEL maintainer="ram0973@gmail.com"
RUN adduser --system springapp
USER springapp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080