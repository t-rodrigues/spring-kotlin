package com.mercadolivro.controllers.responses

data class FieldErrorResponse(
    var field: String,
    var message: String
)
