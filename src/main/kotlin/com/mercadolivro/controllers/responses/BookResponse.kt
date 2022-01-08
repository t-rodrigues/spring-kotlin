package com.mercadolivro.controllers.responses

import com.fasterxml.jackson.annotation.JsonInclude
import com.mercadolivro.enums.BookStatus
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BookResponse(
    var id: Long? = null,
    var title: String,
    var price: BigDecimal,
    var customer: CustomerResponse? = null,
    var status: BookStatus? = null
)
