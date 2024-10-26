package com.jeancot.naves.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Api Naves",
                version = "1.0.0",
                description = "CRUD para api NAVES"
        )
)
public class OpenApiConfig {
}
