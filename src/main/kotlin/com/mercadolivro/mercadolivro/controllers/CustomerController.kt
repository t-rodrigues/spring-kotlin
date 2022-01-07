package com.mercadolivro.mercadolivro.controllers

import com.mercadolivro.mercadolivro.controllers.requests.PostCustomerRequest
import com.mercadolivro.mercadolivro.controllers.requests.PutCustomerRequest
import com.mercadolivro.mercadolivro.extension.toCustomerModel
import com.mercadolivro.mercadolivro.models.CustomerModel
import com.mercadolivro.mercadolivro.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(
    val customerService: CustomerService
) {

    @GetMapping
    fun getCustomers(@RequestParam name: String?): ResponseEntity<List<CustomerModel>> {
        val customers = this.customerService.getCustomers(name)
        return ResponseEntity.ok(customers)
    }

    @GetMapping("/{customerId}")
    fun getCustomerById(@PathVariable customerId: Long): ResponseEntity<CustomerModel> {
        val customerModel = customerService.getCustomerById(customerId)

        return ResponseEntity.ok(customerModel)
    }

    @PostMapping
    fun createCustomer(@RequestBody customer: PostCustomerRequest): ResponseEntity<CustomerModel> {
        val customerModel = customerService.create(customer.toCustomerModel())

        return ResponseEntity.status(HttpStatus.CREATED).body(customerModel)
    }

    @PutMapping("/{customerId}")
    fun updateCustomer(
        @PathVariable customerId: Long, @RequestBody customer: PutCustomerRequest
    ): ResponseEntity<Void> {
        customerService.update(customer.toCustomerModel(customerId))

        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{customerId}")
    fun deleteCustomer(@PathVariable customerId: Long): ResponseEntity<Void> {
        customerService.delete(customerId)

        return ResponseEntity.noContent().build()
    }

}
