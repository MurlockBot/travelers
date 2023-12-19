package com.empresa.travelers.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "TravelersAPP",
        version = "0.0.1",
        description = "Aplicación para agencia de viajes Travelers."))
public class OpenAPIConfig {
}
