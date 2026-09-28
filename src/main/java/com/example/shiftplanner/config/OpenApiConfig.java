package com.example.shiftplanner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ShiftPlanner API Documentations")
                        .version("1.0.0")
                        .description("Employee Shift Roster and Swap Requests REST API")
                        .contact(new Contact().name("ShiftPlanner Support").email("support@shiftplanner.com"))
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
