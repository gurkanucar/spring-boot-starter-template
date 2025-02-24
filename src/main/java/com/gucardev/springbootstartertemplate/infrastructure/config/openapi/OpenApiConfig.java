package com.gucardev.springbootstartertemplate.infrastructure.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(description = "OpenApi documentation", title = "Spring Boot Starter Template", version = "1.0"),
        security = {
                @SecurityRequirement(name = "bearerAuth")
//       ,@SecurityRequirement(name = "apiKeyAuth")
        }
)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerAuth",
                description = "JWT auth description",
                scheme = "bearer",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                in = SecuritySchemeIn.HEADER
        )
//    ,@SecurityScheme(
//        name = "apiKeyAuth",
//        description = "API key authentication",
//        type = SecuritySchemeType.APIKEY,
//        in = SecuritySchemeIn.HEADER,
//        paramName = "API_KEY"
//    )
})
@Configuration
public class OpenApiConfig {

}
