package com.mercadolivro.exceptions

import com.mercadolivro.controllers.responses.ErrorResponse
import com.mercadolivro.controllers.responses.FieldErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.time.Instant

@RestControllerAdvice
class ExceptionsHandler {

    @ExceptionHandler(ObjectNotFoundException::class)
    fun handleObjectNotFoundException(ex: ObjectNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(status = HttpStatus.NOT_FOUND, ex = ex, path = request.contextPath)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(status = HttpStatus.BAD_REQUEST, ex = ex, path = request.contextPath)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException, request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        return generateErrorResponse(status = HttpStatus.UNPROCESSABLE_ENTITY,
            ex = ex,
            message = "Validation failed",
            path = request.contextPath,
            errors = ex.bindingResult.fieldErrors.map { FieldErrorResponse(it.defaultMessage ?: "invalid", it.field) })
    }

    private fun generateErrorResponse(
        status: HttpStatus,
        ex: Exception,
        path: String,
        message: String? = null,
        errors: List<FieldErrorResponse>? = null
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            Instant.now(),
            status = status.value(),
            error = ex::class.simpleName.toString(),
            path = path,
            message = message ?: ex.message.toString(),
            errors = errors
        )

        return ResponseEntity.status(status).body(errorResponse)
    }

}

