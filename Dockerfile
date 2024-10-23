FROM openjdk:21
ADD target/wannacry-0.0.1-SNAPSHOT.jar wannacryapp.jar
ENTRYPOINT [ "java", "-jar","wannacryapp.jar" ]