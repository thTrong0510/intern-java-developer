# Stage 1: Build ứng dụng bằng Gradle
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

# Copy toàn bộ project vào container
COPY . .

# Build project (bỏ qua test để nhanh hơn)
RUN ./gradlew clean build -x test

# Stage 2: Run ứng dụng
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy file jar từ stage build
COPY --from=build /app/build/libs/*.jar app.jar

# Run jar khi container start
ENTRYPOINT ["java", "-jar", "app.jar"]
