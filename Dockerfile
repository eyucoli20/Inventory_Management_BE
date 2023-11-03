FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/*.jar Inventory-Management.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","Inventory-Management.jar"]