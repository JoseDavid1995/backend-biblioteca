FROM openjdk:17
MAINTAINER ctang-dev
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api-tipo-cambio-1.0.jar
ENTRYPOINT ["java","-jar","/api-tipo-cambio-1.0.jar"]