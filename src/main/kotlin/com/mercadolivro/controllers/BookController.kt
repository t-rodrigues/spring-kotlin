package com.mercadolivro.controllers

import com.mercadolivro.controllers.requests.PostBookRequest
import com.mercadolivro.controllers.requests.PutBookRequest
import com.mercadolivro.controllers.responses.BookResponse
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.extension.toResponse
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
    fun getBooks(): List<BookResponse> {
        return bookService.getBooks().map { it.toResponse() }
    }

    @GetMapping("/active")
    fun getActiveBooks(): ResponseEntity<List<BookResponse>> {
        var books = bookService.getActiveBooks()

        return ResponseEntity.ok(books.map { it.toResponse() })
    }

    @GetMapping("/{bookId}")
    fun getBookById(@PathVariable bookId: Long): BookResponse {
        return bookService.getBookById(bookId).toResponse()
    }

    @PostMapping
    fun createBook(@RequestBody request: PostBookRequest): ResponseEntity<BookResponse> {
        val customer = customerService.getCustomerById(request.customerId)
        var bookModel = bookService.create(request.toBookModel(customer))

        return ResponseEntity.status(HttpStatus.CREATED).body(bookModel.toResponse());
    }

    @PutMapping("/{bookId}")
    fun updateBook(@PathVariable bookId: Long, @RequestBody request: PutBookRequest) {
        val book = bookService.getBookById(bookId)
        bookService.update(request.toBookModel(book))
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable bookId: Long) {
        bookService.delete(bookId)
    }

}

