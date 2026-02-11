FROM amazoncorretto:17-alpine

VOLUME /tmp
WORKDIR /app

RUN addgroup -S app && adduser -S app -G app
USER app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]