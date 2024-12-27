# Usa una imagen base de OpenJDK 17
FROM openjdk:17-oracle

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR generado por Maven/Gradle (ajusta el nombre del archivo según sea necesario)
COPY target/prueba_practica_nequi-0.0.1-SNAPSHOT.jar  myapp.jar

# Expon el puerto en el que Spring Boot escuchará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
