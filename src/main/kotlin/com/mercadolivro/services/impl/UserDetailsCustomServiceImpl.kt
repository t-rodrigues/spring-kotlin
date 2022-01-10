package com.mercadolivro.services.impl

import com.mercadolivro.exceptions.AuthException
import com.mercadolivro.repositories.CustomerRepository
import com.mercadolivro.security.UserCustomDetails
import com.mercadolivro.services.UserDetailsCustomService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomServiceImpl(
    private val customerRepository: CustomerRepository
) : UserDetailsCustomService {

    override fun loadUserByUsername(id: String): UserDetails {
        val customer =
            customerRepository.findById(id.toLong()).orElseThrow { AuthException("Email or password invalid") }

        return UserCustomDetails(customer)
    }

}
