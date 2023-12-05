FROM bellsoft/liberica-runtime-container:jdk-17-slim-musl
COPY ./build/libs/bankTransferSystemApi-0.0.1-SNAPSHOT.jar /opt/api
EXPOSE 8080
CMD ["java", "showversion", "-jar", "/opt/app/bankTransferSystemApi-0.0.1-SNAPSHOT.jar"]