package com.mercadolivro.controllers.requests

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostPurchaseRequest(

    @JsonAlias("customer_id")
    @field:NotNull
    @field:Positive
    val customerId: Long,

    @field:NotEmpty
    val books: Set<Long>

)
