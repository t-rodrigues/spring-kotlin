package com.mercadolivro.services

import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel

interface BookService {

    fun getBooks(): List<BookModel>

    fun getActiveBooks(): List<BookModel>

    fun getBookById(bookId: Long): BookModel

    fun create(bookModel: BookModel): BookModel

    fun update(bookModel: BookModel)

    fun delete(bookId: Long)

    fun deleteByCustomer(customer: CustomerModel)

}
