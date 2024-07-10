FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /app
COPY . .
COPY src/main/resources/ec-private-key.pem ./src/main/resources/ec-private-key.pem
COPY src/main/resources/ec-public-key.pem ./src/main/resources/ec-public-key.pem
ENV TOKEN_EXPIRATION_LONG=3600
ENV TOKEN_EXPIRATION_SHORT=600
ENV TOKEN_BLACKLIST_CLEANUP_PERIOD_IN_SEC=3600
ENV PAGE_URL=http://localhost:8080
ENV DB_HOST=srv544364.hstgr.cloud
ENV DB_NAME=time_organizer_test
ENV DB_USER=jakub
ENV DB_PASSWORD=u9TwA2dUjhQS9sw%
ENV DB_DDL_TYPE=create
#ENV SPRING_MAIL_HOST=smtp.example.com
#ENV SPRING_MAIL_PORT=587
#ENV SPRING_MAIL_USERNAME=myusername
#ENV SPRING_MAIL_PASSWORD=mypassword
ENV SPRING_PROFILES_ACTIVE=debug
ENV OAUTH2_GOOGLE_CLIENT_ID=my-google-client-id
ENV OAUTH2_GOOGLE_CLIENT_SECRET=my-google-client-secret
ENV OAUTH2_GOOGLE_REDIRECT_URI=http://localhost:8080/oauth2/callback
ENV SPRING_BOOT_RUN_ARGS=--debug
ENV LOGGING_LEVEL=DEBUG
RUN mvn clean package

FROM amazoncorretto:21.0.2-alpine3.18
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
