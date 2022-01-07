package com.mercadolivro.repositories

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.models.BookModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<BookModel, Long> {

    fun findByStatus(status: BookStatus): List<BookModel>

}
