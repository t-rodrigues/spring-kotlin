package com.mercadolivro.services.impl

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.BookRepository
import com.mercadolivro.services.BookService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    val bookRepository: BookRepository
) : BookService {

    override fun getBooks(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    override fun getActiveBooks(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ACTIVE, pageable)
    }

    override fun getBookById(bookId: Long): BookModel {
        return bookRepository.findById(bookId).orElseThrow()
    }

    override fun create(bookModel: BookModel): BookModel {
        return bookRepository.save(bookModel)
    }

    override fun update(bookModel: BookModel) {
        bookRepository.save(bookModel)
    }

    override fun delete(bookId: Long) {
        val book = getBookById(bookId)
        book.status = BookStatus.CANCELED
        update(book)
    }

    override fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomerAndStatus(customer, BookStatus.ACTIVE)
        books.iterator().forEach { it.status = BookStatus.DELETED }
        bookRepository.saveAll(books)
    }

}
