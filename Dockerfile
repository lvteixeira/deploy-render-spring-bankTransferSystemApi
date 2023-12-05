FROM bellsoft/liberica-runtime-container:jdk-17-slim-musl
CMD ["./gradlew", "clean", "bootJar"] 
COPY deploy-render-spring-bankTransferSystemApi/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "showversion", "-jar", "/app.jar"]