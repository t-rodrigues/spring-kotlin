package com.mercadolivro.controllers.requests

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostBookRequest(
    @field:NotBlank
    var title: String,

    @field:DecimalMin("0.01")
    var price: BigDecimal,

    @JsonAlias("customer_id")
    @field:Positive
    var customerId: Long
)
