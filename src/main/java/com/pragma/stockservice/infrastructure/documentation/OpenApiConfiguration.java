package com.pragma.stockservice.infrastructure.documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        info = @Info(
                title = "Stock Service API",
                description = "API documentation for the Stock Microservice, which manages the categories, items, and inventory operations.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Adan2Net",
                        email = "adan2net@protonmail.me"
                ),
                license = @License(
                        name = "Standard Software Use License for Pragma",
                        url = "https://www.pragma.com/license"
                )
        ),
        servers = {
                @Server(
                        description = "Development Server",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production Server",
                        url = "https://api.pragma.com/stockservice"
                )
        }
)
public class OpenApiConfiguration {
}