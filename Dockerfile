FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine-3.21
RUN addgroup -S appgroup && adduser -S usuario -G appgroup
WORKDIR /myapp
ENV JWT_SECRET=""
COPY --from=build /app/target/*.jar app.jar
RUN chown -R usuario:appgroup /myapp
USER usuario
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
