package com.diwakarallu.ecommerce.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI orderServiceAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Service API")
                        .description("This is the REST API for Order Service")
                        .version("v1.0.0")
                        .contact(new Contact().name("Diwakar Allu").email("diwakar.allu.3435@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Local dev server"),
                        new Server().url("https://api.order-service.com").description("Production server")
                ))
                .externalDocs(new ExternalDocumentation()
                        .description("Order Service Wiki Documentation")
                        .url("https://order-service.com/docs"));
    }
}
