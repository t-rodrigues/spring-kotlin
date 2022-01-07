package com.mercadolivro.services.impl

import com.mercadolivro.models.BookModel
import com.mercadolivro.repositories.BookRepository
import com.mercadolivro.services.BookService
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    val bookRepository: BookRepository
) : BookService {

    override fun create(bookModel: BookModel): BookModel {
        return bookRepository.save(bookModel)
    }

}
