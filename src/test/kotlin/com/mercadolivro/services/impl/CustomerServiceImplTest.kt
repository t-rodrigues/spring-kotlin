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
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
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
    @SpyK
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
    fun `should getCustomerByID throw when invalid id was provided`() {
        val id = Random().nextLong()

        every { customerRepository.findById(id) } returns Optional.empty()

        assertThrows<ObjectNotFoundException> { customerServiceImpl.getCustomerById(id) }
        verify(exactly = 1) { customerRepository.findById(id) }
    }

    @Test
    fun `should update customer`() {
        val id = Random().nextLong()
        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns true
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        assertDoesNotThrow { customerServiceImpl.update(fakeCustomer) }
        verify(exactly = 1) { customerRepository.existsById(id) }
        verify(exactly = 1) { customerRepository.save(fakeCustomer) }
    }

    @Test
    fun `should update throw when invalid id was provided`() {
        val id = Random().nextLong()
        val fakeCustomer = buildCustomer(id)

        every { customerRepository.existsById(id) } returns false

        assertThrows<ObjectNotFoundException> { customerServiceImpl.update(fakeCustomer) }
        verify(exactly = 1) { customerRepository.existsById(id) }
        verify(exactly = 0) { customerRepository.save(any()) }
    }

    @Test
    fun `should delete customer`() {
        val id = Random().nextLong()
        val fakeCustomer = buildCustomer(id)
        val expectedCustomer = fakeCustomer.copy(status = CustomerStatus.INACTIVE)

        every { customerServiceImpl.getCustomerById(id) } returns fakeCustomer
        every { bookService.deleteByCustomer(fakeCustomer) } just runs
        every { customerRepository.save(expectedCustomer) } returns expectedCustomer

        assertDoesNotThrow { customerServiceImpl.delete(id) }

        verify(exactly = 1) { customerServiceImpl.getCustomerById(id) }
        verify(exactly = 1) { bookService.deleteByCustomer(fakeCustomer) }
        verify(exactly = 1) { customerRepository.save(expectedCustomer) }
    }

    @Test
    fun `should delete throws when invalid id was provided`() {
        val id = Random().nextLong()

        every { customerServiceImpl.getCustomerById(id) } throws ObjectNotFoundException("")

        assertThrows<ObjectNotFoundException> { customerServiceImpl.delete(id) }

        verify(exactly = 1) { customerServiceImpl.getCustomerById(id) }
        verify(exactly = 0) { bookService.deleteByCustomer(any()) }
        verify(exactly = 0) { customerRepository.save(any()) }
    }

    @Test
    fun `should return true when email available`() {
        val email = "${Random().nextLong()}@email.com"

        every { customerRepository.existsByEmail(email) } returns false

        val emailAvailable = customerServiceImpl.emailAvailable(email)

        assert(emailAvailable)

        verify(exactly = 1) { customerRepository.existsByEmail(email) }
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
