
FROM openjdk:17.0.1-jdk-slim
EXPOSE 8080
ARG JAR_FILE=target/nexus-services.jar
ADD ${JAR_FILE} nexus-services.jar
ENTRYPOINT ["java","-jar","/nexus-services.jar"]