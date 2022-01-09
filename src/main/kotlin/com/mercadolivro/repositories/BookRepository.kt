package com.mercadolivro.repositories

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<BookModel, Long> {

    fun findByStatus(status: BookStatus, pageable: Pageable): Page<BookModel>

    fun findByCustomerAndStatus(customerModel: CustomerModel, status: BookStatus): List<BookModel>

    fun findAllByIdInAndStatus(id: Set<Long>, status: BookStatus): List<BookModel>

}
