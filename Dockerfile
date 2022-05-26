FROM eclipse-temurin:17
COPY *.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]