FROM --platform=linux/amd64 openjdk:17-slim AS builder

WORKDIR /app

#COPY --from=node-builder /app .

COPY . .

RUN ./mvnw clean install -Dmaven.test.skip=true

FROM --platform=linux/amd64 openjdk:17-alpine AS runner

WORKDIR /app

COPY --from=builder /app/target/*.jar  ./core.jar

CMD ["java", "-jar", "core.jar"]
