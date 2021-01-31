FROM adoptopenjdk/openjdk:latest
ADD target/blog-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
