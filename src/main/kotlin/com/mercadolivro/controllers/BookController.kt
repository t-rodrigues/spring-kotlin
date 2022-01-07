package com.mercadolivro.controllers

import com.mercadolivro.controllers.requests.PostBookRequest
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.models.BookModel
import com.mercadolivro.services.BookService
import com.mercadolivro.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(
    var bookService: BookService,
    var customerService: CustomerService
) {

    @GetMapping
    fun getBooks(): List<BookModel> {
        return bookService.getBooks()
    }

    @GetMapping("/active")
    fun getActiveBooks(): ResponseEntity<List<BookModel>> {
        var books = bookService.getActiveBooks()

        return ResponseEntity.ok(books)
    }

    @GetMapping("/{bookId}")
    fun getBookById(@PathVariable bookId: Long): BookModel {
        return bookService.getBookById(bookId)
    }

    @PostMapping
    fun createBook(@RequestBody request: PostBookRequest): ResponseEntity<Any> {
        val customer = customerService.getCustomerById(request.customerId)
        var bookModel = bookService.create(request.toBookModel(customer))

        return ResponseEntity.status(HttpStatus.CREATED).body(bookModel);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable bookId: Long) {
        bookService.delete(bookId)
    }

}

