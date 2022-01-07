package com.mercadolivro.services.impl

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.models.BookModel
import com.mercadolivro.repositories.BookRepository
import com.mercadolivro.services.BookService
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    val bookRepository: BookRepository
) : BookService {

    override fun getBooks(): List<BookModel> {
        return bookRepository.findAll()
    }

    override fun getActiveBooks(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ACTIVE)
    }

    override fun getBookById(bookId: Long): BookModel {
        return bookRepository.findById(bookId).orElseThrow()
    }

    override fun create(bookModel: BookModel): BookModel {
        return bookRepository.save(bookModel)
    }

    override fun delete(bookId: Long) {
        val book = getBookById(bookId)
        book.status = BookStatus.DELETED
        bookRepository.save(book)
    }

}
