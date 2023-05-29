FROM maven:3.9.2-eclipse-temurin-17-alpine as builder


WORKDIR /app
COPY pom.xml .
COPY src ./src


RUN mvn package


FROM eclipse-temurin:17.0.7_7-jre-alpine


COPY --from=builder app/target/nexus-services-*.jar /nexus-services.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/nexus-services.jar"]
