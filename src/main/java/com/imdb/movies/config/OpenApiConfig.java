package com.imdb.movies.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IMDb-Style Movies Platform API")
                        .description("A comprehensive REST API for managing movies, ratings, and reviews")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Movies Platform Team")
                                .email("support@movies.com")
                                .url("https://movies.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8090/api")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.movies.com")
                                .description("Production Server")
                ));
    }
}