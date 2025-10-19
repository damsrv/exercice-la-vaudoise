package fr.vaudoise.insurance.api.config;

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
                        .title("Insurance API")
                        .version("1.0.0")
                        .description("API for managing insurance clients and contracts")
                        .contact(new Contact()
                                .name("Insurance Company")
                                .email("support@insurance.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost")
                                .description("Development server (via Nginx)")
                ));
    }
}