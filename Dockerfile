
FROM openjdk:17-oracle
WORKDIR /app
COPY target/prueba_practica_nequi-0.0.1-SNAPSHOT.jar  myapp.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
