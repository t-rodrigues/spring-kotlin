package com.mercadolivro.services

import com.mercadolivro.models.BookModel

interface BookService {

    fun getBooks(): List<BookModel>

    fun getActiveBooks(): List<BookModel>

    fun getBookById(bookId: Long): BookModel

    fun create(bookModel: BookModel): BookModel

    fun update(bookModel: BookModel)

    fun delete(bookId: Long)

}
