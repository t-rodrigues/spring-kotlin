package com.mercadolivro.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {

    @Bean
    fun api(): Docket = Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.mercadolivro.controllers"))
        .paths(PathSelectors.any())
        .build().apiInfo(apiInfo())

    private fun apiInfo(): ApiInfo = ApiInfoBuilder().title("API Mercado Livro")
        .contact(Contact("Thiago Rodrigues", "https://github.com/t-rodrigues/spring-kotlin", "thiagor_@live.com"))
        .build()
}
