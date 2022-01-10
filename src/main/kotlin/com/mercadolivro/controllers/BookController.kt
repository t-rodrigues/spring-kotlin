package com.mercadolivro.controllers

import com.mercadolivro.controllers.requests.PostBookRequest
import com.mercadolivro.controllers.requests.PutBookRequest
import com.mercadolivro.controllers.responses.BookResponse
import com.mercadolivro.controllers.responses.PageResponse
import com.mercadolivro.extension.toBookModel
import com.mercadolivro.extension.toPageResponse
import com.mercadolivro.extension.toResponse
import com.mercadolivro.services.BookService
import com.mercadolivro.services.CustomerService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/books")
class BookController(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    @GetMapping
    fun getBooks(@PageableDefault(page = 0, size = 15) pageable: Pageable): PageResponse<BookResponse> {
        return bookService.getBooks(pageable).map { it.toResponse() }.toPageResponse()
    }

    @GetMapping("/active")
    fun getActiveBooks(@PageableDefault(page = 0, size = 15) pageable: Pageable): PageResponse<BookResponse> {
        val books = bookService.getActiveBooks(pageable)

        return books.map { it.toResponse() }.toPageResponse()
    }

    @GetMapping("/{bookId}")
    fun getBookById(@PathVariable bookId: Long): BookResponse {
        return bookService.getBookById(bookId).toResponse()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody @Valid request: PostBookRequest): BookResponse {
        val customer = customerService.getCustomerById(request.customerId)
        val bookModel = bookService.create(request.toBookModel(customer))

        return bookModel.toResponse();
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateBook(@PathVariable bookId: Long, @RequestBody @Valid request: PutBookRequest) {
        val book = bookService.getBookById(bookId)
        bookService.update(request.toBookModel(book))
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable bookId: Long) {
        bookService.delete(bookId)
    }

}

