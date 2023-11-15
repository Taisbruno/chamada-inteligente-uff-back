FROM openjdk:17-jdk-slim

COPY smart-roll/pom.xml /usr/src/myapp/

COPY smart-roll/src /usr/src/myapp/src

WORKDIR /usr/src/myapp

RUN apt-get update && apt-get install -y maven

RUN mvn clean install

EXPOSE 8443

CMD ["java", "-jar", "/usr/src/myapp/target/smart-roll-1.0-SNAPSHOT.jar"]