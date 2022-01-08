package com.mercadolivro.exceptions

import com.mercadolivro.controllers.responses.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.Instant

@RestControllerAdvice
class ExceptionsHandler {

    @ExceptionHandler(ObjectNotFoundException::class)
    fun handleObjectNotFoundException(ex: ObjectNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = buildErrorResponse(HttpStatus.NOT_FOUND, ex, request.contextPath)

        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = buildErrorResponse(HttpStatus.BAD_REQUEST, ex, request.contextPath)

        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

    private fun buildErrorResponse(status: HttpStatus, ex: Exception, path: String): ErrorResponse {
        return ErrorResponse(
            Instant.now(),
            status = status.value(),
            error = ex::class.simpleName.toString(),
            message = ex.message.toString(),
            path = path
        )
    }
    
}

