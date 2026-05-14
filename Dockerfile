FROM eclipse-temurin:17

WORKDIR /app

COPY . .

RUN ./mvnw package -DskipTests

CMD ["java","-jar","target/demo-0.0.1-SNAPSHOT.jar"]
