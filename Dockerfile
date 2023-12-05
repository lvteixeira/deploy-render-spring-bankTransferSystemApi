FROM bellsoft/liberica-runtime-container:jdk-17-slim-musl

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper files
COPY gradlew .
COPY gradle gradle

# Copy the build file and settings
COPY build.gradle settings.gradle ./

# Copy the source code
COPY src src

# Build the application
RUN ./gradlew build

# Expose the port that the application will run on
EXPOSE 8080

# Set the entry point to run the application
CMD ["./gradlew", "bootRun"]