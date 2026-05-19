package com.jobsearch.ai.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Job Search AI API", version = "v1", description = "AI Agent Service for Job Search"))
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Value("${GATEWAY_URL:}")
    private String gatewayUrl;

    @Bean
    public OpenAPI openAPI() {
        if (gatewayUrl != null && !gatewayUrl.isBlank()) {
            return new OpenAPI().servers(List.of(new Server().url(gatewayUrl).description("Gateway")));
        }
        return new OpenAPI();
    }
}
