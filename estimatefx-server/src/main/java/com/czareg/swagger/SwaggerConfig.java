package com.czareg.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${project.version}")
    private String projectVersion;

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch("/service/**")
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("Estimatefx-server")
                        .description("Backend for Estimatefx project")
                        .contact(new Contact().name("Cezary Witkowski")
                                .url("https://www.linkedin.com/in/cezary-witkowski-java/")
                                .email("czarecoo@gmail.com"))
                        .version(projectVersion));
    }
}