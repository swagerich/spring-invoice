package com.erich.factura.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openDetails(){
        return new OpenAPI().info(new Info().title("Spring Invoices API")
                .description("Spring  Invoice 3.0")
                .version("v3.0.0")
                .license(new License().name("Apache 2.0").url("https://spring.io")))
                .externalDocs(new ExternalDocumentation()
                        .description("Spring Invoice Proyect Documentation ")
                        .url("https://github.com/swagerich/"));
    }
}
