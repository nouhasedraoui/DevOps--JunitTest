FROM openjdk:17-jdk-alpine
EXPOSE 8089
ADD target/tp-foyerr-5.0.0.jar tp-foyerr.jar
ENTRYPOINT ["java", "-jar", "/tp-foyerr.jar"]
