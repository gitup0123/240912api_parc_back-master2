package com.parc.api.configuration;

import io.swagger.v3.oas.models.Components;  // Importe la classe Components pour configurer les composants de Swagger, tels que les schémas de sécurité.
import io.swagger.v3.oas.models.OpenAPI;  // Importe la classe OpenAPI pour configurer l'objet principal de Swagger.
import io.swagger.v3.oas.models.info.Contact;  // Importe la classe Contact pour définir les informations de contact dans la documentation Swagger.
import io.swagger.v3.oas.models.info.Info;  // Importe la classe Info pour configurer les informations générales sur l'API.
import io.swagger.v3.oas.models.security.SecurityRequirement;  // Importe la classe SecurityRequirement pour définir les exigences de sécurité pour les endpoints.
import io.swagger.v3.oas.models.security.SecurityScheme;  // Importe la classe SecurityScheme pour configurer les schémas de sécurité (JWT dans ce cas).
import org.springframework.context.annotation.Bean;  // Importe l'annotation Bean pour indiquer qu'une méthode retourne un bean géré par Spring.
import org.springframework.context.annotation.Configuration;  // Importe l'annotation Configuration pour déclarer cette classe comme une configuration Spring.

@Configuration  // Indique que cette classe est une configuration Spring qui définit des beans.
public class SwaggerConfig {

    @Bean  // Crée un bean OpenAPI pour configurer Swagger.
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()  // Définition des informations générales sur l'API.
                        .title("Parc Advisor API")  // Le titre de l'API.
                        .version("1.0")  // La version de l'API.
                        .description("Documentation de l'API pour Parc Advisor.")  // La description de l'API.
                        .contact(new Contact()  // Informations de contact pour l'API.
                                .name("Support")  // Nom du contact.
                                .url("http://www.example.com/support")  // URL du support.
                                .email("support@example.com")))  // Email du support.
                // Ajoute la configuration de sécurité pour les endpoints nécessitant un JWT.
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))  // Définit l'exigence de sécurité pour les endpoints, ici avec le schéma "bearerAuth".
                .components(new Components()  // Ajoute les composants pour Swagger, y compris les schémas de sécurité.
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()  // Ajoute un schéma de sécurité nommé "bearerAuth".
                                .type(SecurityScheme.Type.HTTP)  // Définit le type de schéma de sécurité comme HTTP.
                                .scheme("bearer")  // Spécifie que le schéma est un Bearer Token.
                                .bearerFormat("JWT")));  // Indique que le format du Bearer Token est JWT.
    }
}



