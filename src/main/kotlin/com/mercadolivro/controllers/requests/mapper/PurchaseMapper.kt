package com.mercadolivro.controllers.requests.mapper

import com.mercadolivro.controllers.requests.PostPurchaseRequest
import com.mercadolivro.exceptions.BadRequestException
import com.mercadolivro.models.PurchaseModel
import com.mercadolivro.services.BookService
import com.mercadolivro.services.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    fun toModel(postPurchaseRequest: PostPurchaseRequest): PurchaseModel {
        val customer = customerService.getCustomerById(postPurchaseRequest.customerId)
        val books = bookService.getBooksByIds(postPurchaseRequest.books)

        if (books.isEmpty()) {
            throw BadRequestException("invalid books ${postPurchaseRequest.books}")
        }

        val amount = books.sumOf { it.price }

        return PurchaseModel(customer = customer, books = books.toMutableList(), price = amount)
    }

}
