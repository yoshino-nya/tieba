FROM eclipse-temurin:17-jre

WORKDIR /app
COPY target/*.jar app.jar

EXPOSE 4000
ENTRYPOINT ["java", "-jar", "app.jar"]
