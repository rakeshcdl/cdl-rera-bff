package com.cdl.escrow.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

        private static final String BEARER_AUTH = "BearerAuth";

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info()
                                .title("CDL RERA API")
                                .version("1.0")
                                .description("API documentation with JWT auth"))
                        .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH))
                        .components(new io.swagger.v3.oas.models.Components()
                                .addSecuritySchemes(BEARER_AUTH,
                                        new SecurityScheme()
                                                .name("Authorization")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                ));
        }
}
