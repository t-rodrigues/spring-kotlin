package com.mercadolivro.controllers.requests

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

data class PostBookRequest(
    var title: String,
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Long
)
