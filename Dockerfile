FROM openjdk:8
EXPOSE 8080
ADD target/nexus-services.jar nexus-services.jar
ENTRYPOINT ["java","-jar","/nexus-services.jar"]