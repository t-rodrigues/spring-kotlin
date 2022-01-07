package com.mercadolivro.services

import com.mercadolivro.models.CustomerModel

interface CustomerService {

    fun getCustomers(name: String?): List<CustomerModel>

    fun getCustomerById(customerId: Long): CustomerModel

    fun create(customer: CustomerModel): CustomerModel

    fun update(customer: CustomerModel)

    fun delete(customerId: Long)

}
