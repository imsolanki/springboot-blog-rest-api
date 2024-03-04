FROM eclipse-temurin:17

LABEL mentainer="shobhitsingh.e28@gmail.com"

WORKDIR /app
#whenever we run the app the /app directory will be created in the container

COPY target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar /app/springboot-blog-docker.jar

ENTRYPOINT["java","-jar","springboot-blog-docker.jar"]