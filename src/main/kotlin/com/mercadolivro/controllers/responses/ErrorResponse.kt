package com.mercadolivro.controllers.responses

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val timestamp: Instant,
    val status: Int,
    val error: String,
    val errors: List<FieldErrorResponse>? = null,
    val message: String,
    val path: String
)
