# Use the official maven/Java 8 image to create a build artifact:
FROM maven:3.8.1-openjdk-17-slim as builder


# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package

# Use the Official OpenJDK image for a lean production stage of our multi-stage build.
# https://hub.docker.com/_/openjdk
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM openjdk:17-jdk-slim


# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/nexus-services-*.jar /nexus-services.jar

# Run the web service on container startup.
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/nexus-services.jar"]
