package com.mercadolivro.services

import com.mercadolivro.models.BookModel

interface BookService {

    fun create(toBookModel: BookModel): BookModel

}
