FROM openjdk:14
MAINTAINER Abubakar

# Refer to Maven build
ARG JAR_FILE=target/charging.station.management-0.0.1-SNAPSHOT.jar

# set working directory
WORKDIR /opt/app

# copy jar file to the deirectory /opt/app/
COPY ${JAR_FILE} charging-station-management.jar

# Run project container
ENTRYPOINT ["java","-jar","charging-station-management.jar"]