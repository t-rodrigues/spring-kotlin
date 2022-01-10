package com.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mercadolivro.controllers.requests.AuthRequest
import com.mercadolivro.exceptions.AuthException
import com.mercadolivro.repositories.CustomerRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
    private val jwtUtils: JwtUtils
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val authRequest = jacksonObjectMapper().readValue(request.inputStream, AuthRequest::class.java)
            val id = customerRepository.findByEmail(authRequest.email)?.id
            val authToken = UsernamePasswordAuthenticationToken(id, authRequest.password)
            return authenticationManager.authenticate(authToken)

        } catch (ex: Exception) {
            throw AuthException("Authentication failed")
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val id = (authResult.principal as UserCustomDetails).id
        val token = jwtUtils.generateToken(id)
        response.addHeader("Authorization", "Bearer $token")
    }

}
