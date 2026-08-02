# Usamos una imagen base ligera de Java 17 (JRE)
FROM eclipse-temurin:17-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR generado por Maven al contenedor
COPY target/ms-security-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto del servicio de autenticación
EXPOSE 8089

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]