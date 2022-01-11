package com.mercadolivro.controllers

import com.mercadolivro.controllers.requests.PostCustomerRequest
import com.mercadolivro.controllers.requests.PutCustomerRequest
import com.mercadolivro.controllers.responses.CustomerResponse
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.extension.toResponse
import com.mercadolivro.security.AdminCanOnlyAccess
import com.mercadolivro.security.UserCanOnlyAccessTheirOwnResource
import com.mercadolivro.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping
    @AdminCanOnlyAccess
    fun getCustomers(@RequestParam name: String?): ResponseEntity<List<CustomerResponse>> {
        val customers = this.customerService.getCustomers(name)
        return ResponseEntity.ok(customers.map { it.toResponse() })
    }

    @GetMapping("/{customerId}")
    @UserCanOnlyAccessTheirOwnResource
    fun getCustomerById(@PathVariable customerId: Long): ResponseEntity<CustomerResponse> {
        val customerModel = customerService.getCustomerById(customerId)

        return ResponseEntity.ok(customerModel.toResponse())
    }

    @PostMapping
    fun createCustomer(@Valid @RequestBody customer: PostCustomerRequest): ResponseEntity<CustomerResponse> {
        val customerModel = customerService.create(customer.toCustomerModel())

        return ResponseEntity.status(HttpStatus.CREATED).body(customerModel.toResponse())
    }

    @PutMapping("/{customerId}")
    @UserCanOnlyAccessTheirOwnResource
    fun updateCustomer(
        @PathVariable customerId: Long, @RequestBody @Valid customerRequest: PutCustomerRequest
    ): ResponseEntity<Void> {
        val oldCustomer = customerService.getCustomerById(customerId)
        customerService.update(customerRequest.toCustomerModel(oldCustomer))

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{customerId}")
    @UserCanOnlyAccessTheirOwnResource
    fun deleteCustomer(@PathVariable customerId: Long): ResponseEntity<Void> {
        customerService.delete(customerId)

        return ResponseEntity.noContent().build()
    }

}
