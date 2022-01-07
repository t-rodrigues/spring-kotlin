package com.mercadolivro.extension

import com.mercadolivro.controllers.requests.PostBookRequest
import com.mercadolivro.controllers.requests.PostCustomerRequest
import com.mercadolivro.controllers.requests.PutCustomerRequest
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email)
}

fun PutCustomerRequest.toCustomerModel(id: Long): CustomerModel {
    return CustomerModel(id = id, name = this.name, email = this.email)
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(title = this.title, price = this.price, status = BookStatus.ACTIVE, customer = customer)
}
