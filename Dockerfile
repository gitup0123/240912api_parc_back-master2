# Étape 1: Utiliser une image légère OpenJDK 21 pour exécuter l'application
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copier l'artefact JAR pré-construit dans l'image Docker
# COPY blog/target/blog-0.0.1-SNAPSHOT.jar app.jar
# COPY blog/target/api-0.0.1-SNAPSHOT.jar app.jar
COPY api/target/api-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port utilisé par l'application Spring Boot
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]








