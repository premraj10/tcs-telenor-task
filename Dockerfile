FROM openjdk:17-alpine
ADD target/*.jar gs-spring-boot-docker.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "gs-spring-boot-docker.jar"]