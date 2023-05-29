# Use the official maven/Java 8 image to create a build artifact:

FROM openjdk:17-jdk-slim

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.

# Use the Official OpenJDK image for a lean production stage of our multi-stage build.
# https://hub.docker.com/_/openjdk
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds


# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/nexus-services-*.jar /nexus-services.jar

# Run the web service on container startup.
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/nexus-services.jar"]
