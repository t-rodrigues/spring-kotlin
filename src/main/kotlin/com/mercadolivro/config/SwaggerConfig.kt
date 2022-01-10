package com.mercadolivro.config

import io.swagger.models.auth.In
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.mercadolivro.controllers"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo())
        .securityContexts(listOf(securityContext()))
        .securitySchemes(listOf(apiKey()))

    private fun apiInfo(): ApiInfo = ApiInfoBuilder().title("API Mercado Livro")
        .contact(Contact("Thiago Rodrigues", "https://github.com/t-rodrigues/spring-kotlin", "thiagor_@live.com"))
        .build()

    private fun securityContext() = SecurityContext.builder().securityReferences(listOf(defaultAuth())).build()

    private fun defaultAuth() =
        SecurityReference("Authorization", arrayOf(AuthorizationScope("global", "accessEverything")))

    private fun apiKey() = ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, In.HEADER.name)

}

