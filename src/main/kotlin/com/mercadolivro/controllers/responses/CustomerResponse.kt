package com.mercadolivro.controllers.responses

import com.fasterxml.jackson.annotation.JsonInclude
import com.mercadolivro.enums.CustomerStatus

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CustomerResponse(
    val id: Long? = null,
    val name: String,
    val email: String,
    val status: CustomerStatus? = null
)
