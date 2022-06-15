FROM openjdk:8-jdk-alpine
ADD target/job-scheduler-1.0-SNAPSHOT.jar /tmp/appservice/app.jar

ENV PORT 8080
EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.port=8080", "-jar", "/tmp/appservice/app.jar"]