package com.mercadolivro.services

import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BookService {

    fun getBooks(pageable: Pageable): Page<BookModel>

    fun getActiveBooks(pageable: Pageable): Page<BookModel>

    fun getBookById(bookId: Long): BookModel

    fun create(bookModel: BookModel): BookModel

    fun update(bookModel: BookModel)

    fun delete(bookId: Long)

    fun deleteByCustomer(customer: CustomerModel)

}
