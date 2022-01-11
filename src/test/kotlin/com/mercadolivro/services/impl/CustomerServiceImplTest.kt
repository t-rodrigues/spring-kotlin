package com.mercadolivro.services.impl

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Role
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.CustomerRepository
import com.mercadolivro.services.BookService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
internal class CustomerServiceImplTest {

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bcrypt: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var customerServiceImpl: CustomerServiceImpl

    @Test
    fun `should return all customers`() {
        val fakeCustomers = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findAll() } returns fakeCustomers

        val customers = customerServiceImpl.getCustomers(null)

        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) { customerRepository.findAll() }
        verify(exactly = 0) { customerRepository.findAllByNameIsContainingIgnoreCase(any()) }
    }

    private fun buildCustomer(
        id: Long? = null,
        name: String? = "John Doe",
        email: String? = "john-${UUID.randomUUID()}@mail.com",
        password: String = "password"
    ) = CustomerModel(
        id = id,
        name = name!!,
        email = email!!,
        password = password,
        status = CustomerStatus.ACTIVE,
        roles = setOf(Role.CUSTOMER)
    )

}
