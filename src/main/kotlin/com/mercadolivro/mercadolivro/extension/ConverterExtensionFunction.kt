package com.mercadolivro.mercadolivro.extension

import com.mercadolivro.mercadolivro.controllers.requests.PostCustomerRequest
import com.mercadolivro.mercadolivro.controllers.requests.PutCustomerRequest
import com.mercadolivro.mercadolivro.models.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email)
}

fun PutCustomerRequest.toCustomerModel(id: Long): CustomerModel {
    return CustomerModel(id = id, name = this.name, email = this.email)
}
