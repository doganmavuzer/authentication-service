FROM openjdk:17-oracle
LABEL maintaner="ceng.mavuzer@gmail.com"
VOLUME /main-app
ADD build/libs/authentication-service-0.0.1-SNAPSHOT.jar authentication-service.jar
ENTRYPOINT [ "java", "-jar", "/authentication-service.jar"]