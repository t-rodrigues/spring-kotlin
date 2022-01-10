package com.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mercadolivro.controllers.responses.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = MediaType.APPLICATION_JSON.toString()
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val errorResponse =
            ErrorResponse(
                null,
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                null,
                "Unauthorized",
                ""
            )

        response.outputStream.print(jacksonObjectMapper().writeValueAsString(errorResponse))
    }

}
