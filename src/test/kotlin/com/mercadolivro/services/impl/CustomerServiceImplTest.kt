package com.mercadolivro.services.impl

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Role
import com.mercadolivro.exceptions.ObjectNotFoundException
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
import org.junit.jupiter.api.assertThrows
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

    @Test
    fun `should return customers when name is provided`() {
        val name = UUID.randomUUID().toString()
        val fakeCustomers = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findAllByNameIsContainingIgnoreCase(name) } returns fakeCustomers

        val customers = customerServiceImpl.getCustomers(name)

        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) { customerRepository.findAllByNameIsContainingIgnoreCase(name) }
        verify(exactly = 0) { customerRepository.findAll() }
    }

    @Test
    fun `should create customer and encrypt password`() {
        val initialPassword = Random().nextInt().toString()
        val fakeCustomer = buildCustomer(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val fakeCustomerEncrypted = fakeCustomer.copy(password = fakePassword)

        every { customerRepository.save(fakeCustomerEncrypted) } returns fakeCustomer
        every { bcrypt.encode(initialPassword) } returns fakePassword

        val customer = customerServiceImpl.create(fakeCustomer)
        assertEquals(fakeCustomer, customer)
        verify(exactly = 1) { customerRepository.save(fakeCustomerEncrypted) }
        verify(exactly = 1) { bcrypt.encode(fakeCustomer.password) }
    }

    @Test
    fun `should return customer by id`() {
        val id = Random().nextLong()
        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.findById(id) } returns Optional.of(fakeCustomer)

        val customer = customerServiceImpl.getCustomerById(id)

        assertEquals(fakeCustomer, customer)
        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `should throw when invalid id was provided`() {
        val id = Random().nextLong()

        every { customerRepository.findById(id) } returns Optional.empty()

        assertThrows<ObjectNotFoundException> { customerServiceImpl.getCustomerById(id) }
        verify(exactly = 1) { customerRepository.findById(id) }
    }

    private fun buildCustomer(
        id: Long? = null,
        name: String? = "customer-name",
        email: String? = "${UUID.randomUUID()}@mail.com",
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
