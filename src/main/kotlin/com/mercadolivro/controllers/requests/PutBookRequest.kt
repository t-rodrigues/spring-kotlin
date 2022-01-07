package com.mercadolivro.controllers.requests

import java.math.BigDecimal

data class PutBookRequest(
    var title: String?,
    var price: BigDecimal?
)
