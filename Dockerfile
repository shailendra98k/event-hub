#FROM node:20-alpine AS node-builder
#
#WORKDIR /app
#
## Copy the package.json and package-lock.json (if using npm) or yarn.lock (if using yarn) to the container
#COPY package*.json ./
#
## Install the app's dependencies
#RUN npm install --legacy-peer-deps
#
## Copy the rest of the application code to the container
#COPY . .
#
## Build the React app for production
#RUN npm run build


FROM --platform=linux/amd64 openjdk:17-slim AS builder

WORKDIR /app

#COPY --from=node-builder /app .

RUN ./mvnw clean compile install -Dmaven.test.skip=true

FROM --platform=linux/amd64 openjdk:17-alpine AS runner

WORKDIR /app

COPY --from=builder /app/target/*.jar  ./core.jar

CMD ["java", "-jar", "core.jar"]
