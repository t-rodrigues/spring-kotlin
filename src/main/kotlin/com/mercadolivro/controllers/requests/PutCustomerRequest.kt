package com.mercadolivro.controllers.requests

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class PutCustomerRequest(
    @field:NotBlank
    var name: String,

    @field:NotBlank
    @field:Email
    var email: String
)
