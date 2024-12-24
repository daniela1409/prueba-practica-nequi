FROM openjdk:17-oracle
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=qa","-jar","prueba_practica_nequi-0.0.1-SNAPSHOT.jar"]
COPY target/prueba_practica_nequi-0.0.1-SNAPSHOT.jar .
