FROM openjdk:11-alpine
ADD target/*.jar gs-spring-boot-docker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "gs-spring-boot-docker.jar"]