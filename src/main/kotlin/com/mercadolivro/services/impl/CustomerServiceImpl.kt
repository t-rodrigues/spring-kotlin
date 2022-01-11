package com.mercadolivro.services.impl

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Role
import com.mercadolivro.exceptions.ObjectNotFoundException
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.CustomerRepository
import com.mercadolivro.services.BookService
import com.mercadolivro.services.CustomerService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val bookService: BookService,
    private val bcrypt: BCryptPasswordEncoder
) : CustomerService {

    override fun getCustomers(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findAllByNameIsContainingIgnoreCase(it)
        }
        return customerRepository.findAll()
    }

    override fun getCustomerById(customerId: Long): CustomerModel {
        return customerRepository.findById(customerId)
            .orElseThrow { ObjectNotFoundException("Customer $customerId not found") }
    }

    override fun create(customer: CustomerModel): CustomerModel {
        val customerCopy = customer.copy(
            roles = setOf(Role.CUSTOMER),
            password = bcrypt.encode(customer.password)
        )
        return customerRepository.save(customerCopy)
    }

    override fun update(customer: CustomerModel) {
        if (!customerRepository.existsById(customer.id!!)) {
            throw ObjectNotFoundException("Customer ${customer.id} not found")
        }
        customerRepository.save(customer)
    }

    override fun delete(customerId: Long) {
        val customer = getCustomerById(customerId)
        bookService.deleteByCustomer(customer)
        customer.status = CustomerStatus.INACTIVE
        customerRepository.save(customer)
    }

    override fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }

}
