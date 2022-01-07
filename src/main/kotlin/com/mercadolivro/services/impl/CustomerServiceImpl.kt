package com.mercadolivro.services.impl

import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repositories.CustomerRepository
import com.mercadolivro.services.CustomerService
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(val customerRepository: CustomerRepository) : CustomerService {

    override fun getCustomers(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findAllByNameIsContainingIgnoreCase(it)
        }
        return customerRepository.findAll()
    }

    override fun getCustomerById(customerId: Long): CustomerModel {
        return customerRepository.findById(customerId).orElseThrow()
    }

    override fun create(customer: CustomerModel): CustomerModel {
        if (customerRepository.existsByEmail(customer.email)) {
            throw Exception()
        }
        return customerRepository.save(customer)
    }

    override fun update(customer: CustomerModel) {
        if (!customerRepository.existsById(customer.id!!)) {
            throw Exception()
        }
        customerRepository.save(customer)
    }

    override fun delete(customerId: Long) {
        if (!customerRepository.existsById(customerId)) {
            throw Exception()
        }
        customerRepository.deleteById(customerId)
    }

}
